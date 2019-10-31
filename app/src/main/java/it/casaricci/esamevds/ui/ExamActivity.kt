/*
 * EsameVDS Android app
 * Copyright (c) 2019 Daniele Ricci
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import it.casaricci.esamevds.R
import kotlinx.android.synthetic.main.activity_exam.*

class ExamActivity : AppCompatActivity(), ExamContainer {

    private lateinit var viewModel: ExamViewModel

    private val answeredCount: Int
        get() = viewModel.answers?.count { value -> value != null } ?: 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)

        button_next.setOnClickListener {
            nextQuestion()
        }
        button_previous.setOnClickListener {
            previousQuestion()
        }
        button_terminate.setOnClickListener {
            MaterialDialog(this).show {
                message(text = resources.getQuantityString(R.plurals.message_terminate_exam, answeredCount, answeredCount))
                positiveButton(android.R.string.ok) {
                    terminate()
                }
                negativeButton(android.R.string.cancel)
            }
        }

        viewModel = ExamViewModel(this)

        if (savedInstanceState == null) {
            var startNew = true
            val resumeExamId = intent.getLongExtra(EXTRA_RESUME, 0)
            if (resumeExamId > 0) {
                try {
                    viewModel.resume(resumeExamId)
                    startNew = false
                }
                catch (e: IllegalStateException) {
                    Log.w("EsameVDS", "Unable to resume exam", e)
                }
            }

            // no resume data or new exam, start now!
            if (startNew) {
                val specialty: Int = intent.getIntExtra(EXTRA_SPECIALTY, -1)
                // blocking, but who cares
                viewModel.start(specialty)
            }
        }
        else {
            viewModel.onRestoreInstanceState(savedInstanceState)
        }

        progress_exam.max = viewModel.examData!!.questions.size

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        MaterialDialog(this).show {
            message(R.string.message_abort_exam)
            positiveButton(android.R.string.ok) {
                viewModel.cancel()
                super.onBackPressed()
            }
            negativeButton(android.R.string.cancel)
        }
    }

    override fun storeAnswer(answer: Int) {
        // blocking, but who cares
        viewModel.storeAnswer(answer)
    }

    private fun nextQuestion() {
        // blocking, but who cares
        viewModel.nextQuestion()
        updateQuestion()
    }

    private fun previousQuestion() {
        // blocking, but who cares
        viewModel.previousQuestion()
        updateQuestion()
    }

    private fun terminate() {
        viewModel.terminate()
        ResultActivity.start(
            this,
            viewModel.examData!!,
            viewModel.answers!!
        )
        finish()
    }

    private fun updateQuestion() {
        progress_exam.progress = viewModel.currentQuestion
        text_progress.text = getString(
            R.string.exam_progress,
            viewModel.currentQuestion + 1, viewModel.examData!!.questions.size)

        button_next.isEnabled = viewModel.currentQuestion < (viewModel.examData!!.questions.size - 1)
        button_previous.isEnabled = viewModel.currentQuestion > 0

        val f = QuestionFragment.newInstance(
            viewModel.question,
            viewModel.answer,
            false
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.question_fragment, f)
            .commit()
    }

    companion object {
        private const val EXTRA_SPECIALTY = "specialty"
        private const val EXTRA_RESUME = "resume"

        fun start(context: Context, specialty: Int) {
            val i = Intent(context, ExamActivity::class.java)
            i.putExtra(EXTRA_SPECIALTY, specialty)
            context.startActivity(i)
        }

        fun resume(context: Context, specialty: Int, resume: Long?) {
            val i = Intent(context, ExamActivity::class.java)
            i.putExtra(EXTRA_SPECIALTY, specialty)
            i.putExtra(EXTRA_RESUME, resume)
            context.startActivity(i)
        }
    }

}
