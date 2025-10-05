package it.casaricci.esamevds.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import it.casaricci.esamevds.R
import it.casaricci.esamevds.data.ExamSubject
import it.casaricci.esamevds.databinding.ActivityGameChoiceBinding
import it.casaricci.esamevds.persistence.AppDatabase
import it.casaricci.esamevds.persistence.ExamRepository

class GameChoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameChoiceBinding

    private val specialty: Int
        get() = intent.getIntExtra(EXTRA_SPECIALTY, -1)

    private lateinit var repository: ExamRepository
    private var ongoingExamId: Long? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = ExamRepository(AppDatabase(application))

        binding.subtitle.text = resources.getStringArray(R.array.specialty_names)[specialty]

        binding.buttonExam.setOnClickListener {
            doExamSimulation()
        }
        binding.buttonSubject.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.title_subjects)
                listItems(R.array.subject_names) { dialog, index, _ ->
                    QuickieActivity.start(
                        dialog.context,
                        specialty,
                        ExamSubject[index + 1],
                        false
                    )
                }
            }
        }
        binding.buttonQuickie.setOnClickListener {
            QuickieActivity.start(
                this,
                specialty,
                ExamSubject.ALL,
                true
            )
        }
    }

    override fun onResume() {
        super.onResume()

        val ongoingExam = repository.getOngoingExam(specialty)
        if (ongoingExam != null) {
            ongoingExamId = ongoingExam.id

            val ongoingExamStats = repository.getExamStats(ongoingExam.id)
            binding.textInfoExam.text = getString(R.string.text_info_exam_in_progress,
                ongoingExamStats!!.answers, ongoingExamStats.questions)
            binding.textInfoExam.setTextColor(ContextCompat.getColor(this, R.color.textNotPassed))
        }
        else {
            ongoingExamId = null

            binding.textInfoExam.text = getString(R.string.text_info_exam)
            // TODO original style color
            binding.textInfoExam.setTextColor(ContextCompat.getColor(this, android.R.color.black))
        }
    }

    private fun doExamSimulation() {
        if (ongoingExamId != null) {
            MaterialDialog(this).show {
                message(R.string.message_exam_in_progress)
                positiveButton(R.string.button_exam_in_progress_resume) { dialog ->
                    ExamActivity.resume(
                        dialog.context,
                        specialty,
                        ongoingExamId!!
                    )
                }
                negativeButton(R.string.button_exam_in_progress_new) { dialog ->
                    repository.cancelExam(ongoingExamId!!)
                    ExamActivity.start(dialog.context, specialty)
                }
            }
        }
        else {
            ExamActivity.start(this, specialty)
        }
    }

    companion object {
        private const val EXTRA_SPECIALTY = "specialty"

        fun start(context: Context, specialty: Int) {
            val i = Intent(context, GameChoiceActivity::class.java)
            i.putExtra(EXTRA_SPECIALTY, specialty)
            context.startActivity(i)
        }
    }

}
