package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import it.casaricci.esamevds.R
import it.casaricci.esamevds.databinding.ActivityExamBinding

class ExamActivity : AppCompatActivity(), ExamContainer {

    private lateinit var binding: ActivityExamBinding

    private lateinit var viewModel: ExamViewModel

    private val answeredCount: Int
        get() = viewModel.answers?.count { value -> value != null } ?: 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener {
            nextQuestion()
        }
        binding.buttonPrevious.setOnClickListener {
            previousQuestion()
        }
        binding.buttonTerminate.setOnClickListener {
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

        binding.progressExam.max = viewModel.examData!!.questions.size

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
        binding.progressExam.progress = viewModel.currentQuestion
        binding.textProgress.text = getString(
            R.string.exam_progress,
            viewModel.currentQuestion + 1, viewModel.examData!!.questions.size)

        binding.buttonNext.isEnabled = viewModel.currentQuestion < (viewModel.examData!!.questions.size - 1)
        binding.buttonPrevious.isEnabled = viewModel.currentQuestion > 0

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
