package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import it.casaricci.esamevds.R
import it.casaricci.esamevds.data.ExamData
import it.casaricci.esamevds.data.ExamQuestionResult
import it.casaricci.esamevds.data.ExamResult
import it.casaricci.esamevds.ui.adapter.RecapAdapter
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {

    private lateinit var examData: ExamData
    private lateinit var examAnswers: Array<Int?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        examData = intent.getParcelableExtra(EXTRA_EXAM_DATA)!!
        examAnswers = intent.getIntegerArrayListExtra(EXTRA_ANSWERS)!!.toTypedArray()

        // TODO not really a good place for this
        text_result_passed.setOnClickListener {
            PrintResultActivity.start(this, examData, examAnswers)
        }

        text_specialty.text = resources.getStringArray(R.array.specialty_names)[examData.specialty]

        val examResult = ExamResult.create(examData, examAnswers)

        val passed = examResult.passed
        text_result_passed.setText(if (passed) R.string.exam_passed else R.string.exam_not_passed)
        text_result_passed.setTextColor(ContextCompat.getColor(this, if (passed) R.color.textPassed else R.color.textNotPassed))

        text_answers_correct.text = examResult.numCorrect.toString()
        text_answers_wrong.text = examResult.numWrong.toString()
        text_answers_unanswered.text = examResult.numUnanswered.toString()

        // recap adapter: include wrong answers only
        val recapAdater = RecapAdapter(this)
        for (i in examData.questions.indices) {
            if (examAnswers[i] == null || examAnswers[i] != examData.questions[i].correctAnswer) {
                recapAdater.add(ExamQuestionResult(examData.questions[i], examAnswers[i]))
            }
        }

        list_recap.adapter = recapAdater
    }

    companion object {
        private const val EXTRA_EXAM_DATA = "examData"
        private const val EXTRA_ANSWERS = "answers"

        fun start(context: Context, examData: ExamData, answers: Array<Int?>) {
            val i = Intent(context, ResultActivity::class.java)
            i.putExtra(EXTRA_EXAM_DATA, examData)
            i.putIntegerArrayListExtra(EXTRA_ANSWERS, answers.toCollection(ArrayList()))
            context.startActivity(i)
        }
    }

}
