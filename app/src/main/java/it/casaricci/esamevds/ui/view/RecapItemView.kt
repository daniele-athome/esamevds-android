package it.casaricci.esamevds.ui.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import it.casaricci.esamevds.ui.QuestionFragment
import it.casaricci.esamevds.R
import it.casaricci.esamevds.data.ExamQuestion

class RecapItemView: LinearLayout {

    constructor(context: Context): super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
    }

    fun bind(question: ExamQuestion, answer: Int?) {
        QuestionFragment.bindQuestion(context, question, answer, this)
        // mark correct and wrong answers
        val correctRadioButton = QuestionFragment
            .getAnswerRadioButton(question.correctAnswer, this)

        // we are handling wrongly answered questions here, so if an answer was given it was wrong
        answer?.let {
            QuestionFragment.getAnswerRadioButton(answer, this)
        // FIXME I don't like this: not using themes, but it seems impossible to change style without reloading the whole context
        }?.setTextColor(ContextCompat.getColor(context, R.color.textNotPassed))

        // FIXME I don't like this: not using themes, but it seems impossible to change style without reloading the whole context
        correctRadioButton.setTextColor(ContextCompat.getColor(context, R.color.textPassed))
        //correctRadioButton.buttonTintList = ResourcesCompat.getColorStateList(resources,
        //    R.color.radio_correct, context.theme)
        correctRadioButton.isChecked = true
    }

    fun unbind() {
        // TODO
    }

}
