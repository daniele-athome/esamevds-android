package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import it.casaricci.esamevds.R
import it.casaricci.esamevds.data.ExamData
import it.casaricci.esamevds.data.ExamQuestionResult
import it.casaricci.esamevds.data.ExamResult
import it.casaricci.esamevds.databinding.ActivityResultBinding
import it.casaricci.esamevds.ui.adapter.RecapAdapter

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private lateinit var examData: ExamData
    private lateinit var examAnswers: Array<Int?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        examData = IntentCompat.getParcelableExtra(intent,EXTRA_EXAM_DATA, ExamData::class.java)!!
        examAnswers = intent.getIntegerArrayListExtra(EXTRA_ANSWERS)!!.toTypedArray()

        // TODO not really a good place for this
        binding.textResultPassed.setOnClickListener {
            PrintResultActivity.start(this, examData, examAnswers)
        }

        binding.textSpecialty.text = resources.getStringArray(R.array.specialty_names)[examData.specialty]

        val examResult = ExamResult.create(examData, examAnswers)

        val passed = examResult.passed
        binding.textResultPassed.setText(if (passed) R.string.exam_passed else R.string.exam_not_passed)
        binding.textResultPassed.setTextColor(ContextCompat.getColor(this, if (passed) R.color.textPassed else R.color.textNotPassed))

        binding.textAnswersCorrect.text = examResult.numCorrect.toString()
        binding.textAnswersWrong.text = examResult.numWrong.toString()
        binding.textAnswersUnanswered.text = examResult.numUnanswered.toString()

        // recap adapter: include wrong answers only
        val recapAdater = RecapAdapter(this)
        for (i in examData.questions.indices) {
            if (examAnswers[i] == null || examAnswers[i] != examData.questions[i].correctAnswer) {
                recapAdater.add(ExamQuestionResult(examData.questions[i], examAnswers[i]))
            }
        }

        binding.listRecap.adapter = recapAdater
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
