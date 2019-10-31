package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import esame_vds.ExamDataAccess
import it.casaricci.esamevds.R
import it.casaricci.esamevds.data.ExamSubject
import kotlinx.android.synthetic.main.activity_quickie.*

class QuickieActivity : AppCompatActivity(), ExamContainer {

    private lateinit var viewModel: QuickieViewModel

    private val specialty: Int
        get() = intent.getIntExtra(EXTRA_SPECIALTY, -1)

    private val subject: ExamSubject
        get() = ExamSubject[intent.getIntExtra(EXTRA_SUBJECT, 0)]!!

    private val loop: Boolean
        get() = intent.getBooleanExtra(EXTRA_LOOP, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quickie)

        if (loop) {
            button_previous.visibility = View.GONE
        }
        else {
            button_previous.setOnClickListener {
                previousQuestion()
            }
        }
        button_next.setOnClickListener {
            nextQuestion()
        }

        viewModel = QuickieViewModel()

        if (savedInstanceState == null) {
            reloadQuestions()
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

    private fun reloadQuestions() {
        // FIXME getAllQuestions doesn't work because it's fixed to 70 questions
        viewModel.examData = ExamDataAccess.getExamData(specialty, subject)
    }

    override fun storeAnswer(answer: Int) {
        viewModel.storeAnswer(answer)
    }

    private fun previousQuestion() {
        viewModel.previousQuestion()
        updateQuestion()
    }

    private fun nextQuestion() {
        if (viewModel.currentQuestion < (viewModel.examData!!.questions.size - 1)) {
            viewModel.nextQuestion()
        }
        else if (loop) {
            reloadQuestions()
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        progress_exam.progress = viewModel.currentQuestion
        text_progress.text = getString(
            R.string.exam_progress,
            viewModel.currentQuestion + 1, viewModel.examData!!.questions.size)

        if (!loop) {
            button_next.isEnabled = viewModel.currentQuestion < (viewModel.examData!!.questions.size - 1)
            button_previous.isEnabled = viewModel.currentQuestion > 0
        }

        val f = QuestionFragment.newInstance(
            viewModel.question,
            viewModel.answer,
            true
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.question_fragment, f)
            .commit()
    }

    companion object {
        private const val EXTRA_SPECIALTY = "specialty"
        private const val EXTRA_SUBJECT = "subject"
        private const val EXTRA_LOOP = "loop"

        /**
         * Start this activity.
         * @param context context to launch the activity
         * @param specialty exam specialty
         * @param subject subject of the questions
         * @param loop true to loop questions and hide the Previous button
         */
        fun start(context: Context, specialty: Int, subject: ExamSubject?, loop: Boolean) {
            val i = Intent(context, QuickieActivity::class.java)
            i.putExtra(EXTRA_SPECIALTY, specialty)
            subject?.let {
                i.putExtra(EXTRA_SUBJECT, it.index)
            }
            i.putExtra(EXTRA_LOOP, loop)
            context.startActivity(i)
        }
    }

}
