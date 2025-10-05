package it.casaricci.esamevds.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import it.casaricci.esamevds.R
import it.casaricci.esamevds.Utils
import it.casaricci.esamevds.data.ExamQuestion
import it.casaricci.esamevds.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    companion object {
        fun newInstance(question: ExamQuestion, answer: Int?, checkAnswer: Boolean): QuestionFragment {
            val f = QuestionFragment()
            val args = Bundle()
            args.putParcelable("question", question)
            args.putInt("chosenAnswer", answer ?: -1)
            args.putBoolean("checkAnswer", checkAnswer)
            f.arguments = args
            return f
        }

        /**
         * Used in a list view item context too, so be sure to reset all view status.
         */
        fun bindQuestion(context: Context, question: ExamQuestion, answer: Int?, viewGroup: ViewGroup) {
            viewGroup.findViewById<TextView>(R.id.text_question).text = question.question

            viewGroup.findViewById<TextView>(R.id.text_subject)?.let {
                with (it) {
                    // FIXME this check should go away
                    if (question.subject.index > 0) {
                        val subjectNames = context.resources.getStringArray(R.array.subject_names)
                        text = subjectNames[question.subject.index - 1]
                        visibility = View.VISIBLE
                    }
                    else {
                        visibility = View.GONE
                    }
                }
            }

            val imagePicture = viewGroup.findViewById<ImageView>(R.id.image_picture)
            if (question.picturePath != null) {
                val imageResource = QuestionFragment::class.java.classLoader!!.getResourceAsStream(question.picturePath)
                val imageBitmap = BitmapFactory.decodeStream(imageResource)
                val scaledBitmap = Utils.resizeBitmap(context, imageBitmap)
                if (scaledBitmap != imageBitmap)
                    imageBitmap.recycle()

                imagePicture.setImageBitmap(scaledBitmap)
                imagePicture.requestLayout()
                imagePicture.visibility = View.VISIBLE
            }
            else {
                imagePicture.visibility = View.GONE
            }

            val textAnswers = arrayOf<RadioButton>(
                viewGroup.findViewById(R.id.radio_answer1),
                viewGroup.findViewById(R.id.radio_answer2),
                viewGroup.findViewById(R.id.radio_answer3),
                viewGroup.findViewById(R.id.radio_answer4)
            )

            for (i in textAnswers.indices) {
                if (i < question.answers.size) {
                    textAnswers[i].text = question.answers[i]
                    // FIXME I don't like this: not using themes, but it seems impossible to change style without reloading the whole context
                    // reset tint
                    //textAnswers[i].buttonTintList = defaultRadioColor
                    textAnswers[i].setTextColor(ContextCompat.getColor(context, android.R.color.black))
                    textAnswers[i].visibility = View.VISIBLE
                    textAnswers[i].isChecked = (answer != null && (textAnswers[i].tag as String).toInt() == answer)
                }
                else {
                    textAnswers[i].visibility = View.GONE
                }
            }
        }

        fun getAnswerRadioButton(answer: Int, viewGroup: ViewGroup): RadioButton = when (answer) {
            0 -> viewGroup.findViewById(R.id.radio_answer1)
            1 -> viewGroup.findViewById(R.id.radio_answer2)
            2 -> viewGroup.findViewById(R.id.radio_answer3)
            3 -> viewGroup.findViewById(R.id.radio_answer4)
            else -> throw IllegalArgumentException(answer.toString())
        }
    }

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private lateinit var question: ExamQuestion
    private var answer: Int? = null

    private lateinit var textAnswers: Array<RadioButton>

    private val examContainer: ExamContainer?
        get() = this.context as ExamContainer

    private val isCheckAnswer: Boolean
        get() = arguments?.getBoolean("checkAnswer", false) ?: false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chosenAnswer: Int = requireArguments().getInt("chosenAnswer")
        question = BundleCompat.getParcelable(
            requireArguments(), "question", ExamQuestion::class.java)!!
        answer = if (chosenAnswer < 0) null else chosenAnswer
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textAnswers = arrayOf(
            binding.radioAnswer1,
            binding.radioAnswer2,
            binding.radioAnswer3,
            binding.radioAnswer4
        )
        for (radioAnswer in textAnswers) {
            radioAnswer.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    val answer = (buttonView.tag as String).toInt()
                    if (isCheckAnswer) {
                        checkAnswer(answer)
                    }
                    requireArguments().putInt("chosenAnswer", answer)
                    examContainer?.storeAnswer(answer)
                }
            }
        }

        loadQuestion()
    }

    private fun checkAnswer(answer: Int) {
        // FIXME we should turn the whole compound color
        for (i in textAnswers.indices) {
            with (textAnswers[i]) {
                if (i == answer) {
                    val color = if (answer == question.correctAnswer) R.color.textPassed
                        else R.color.textNotPassed
                    setTextColor(ContextCompat.getColor(context, color))
                }
                else {
                    setTextColor(ContextCompat.getColor(context, android.R.color.black))
                }
            }
        }
    }

    private fun loadQuestion() {
        bindQuestion(
            requireContext(),
            question,
            answer,
            view as ViewGroup
        )
        if (isCheckAnswer) {
            answer?.let {
                checkAnswer(it)
            }
        }
    }

}
