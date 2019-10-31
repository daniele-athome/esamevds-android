package it.casaricci.esamevds.ui

import android.os.Bundle
import it.casaricci.esamevds.data.ExamData
import it.casaricci.esamevds.data.ExamQuestion

class QuickieViewModel {

    var examData: ExamData? = null
        set(value) {
            field = value
            answers = if (value != null) arrayOfNulls(value.questions.size) else null
            currentQuestion = 0
        }

    var answers: Array<Int?>? = null
        private set

    val question: ExamQuestion
        get() = examData!!.questions[currentQuestion]

    val answer: Int?
        get() = answers?.get(currentQuestion)

    var currentQuestion: Int = 0
        private set

    fun nextQuestion() {
        currentQuestion++
    }

    fun previousQuestion() {
        currentQuestion = currentQuestion.dec().coerceAtLeast(0)
    }

    fun storeAnswer(answer: Int?) {
        answers?.set(currentQuestion, answer)
    }

    fun onSaveInstanceState(bundle: Bundle) {
        this.examData?.let { bundle.putParcelable("ExamViewModel.examData", it) }
        this.answers?.let { bundle.putIntegerArrayList("ExamViewModel.answers", ArrayList(it.toList())) }
        bundle.putInt("ExamViewModel.currentQuestion", this.currentQuestion)
    }

    fun onRestoreInstanceState(bundle: Bundle) {
        if (bundle.containsKey("ExamViewModel.examData")) {
            this.examData = bundle.getParcelable("ExamViewModel.examData")
        }
        if (bundle.containsKey("ExamViewModel.answers")) {
            this.answers = bundle.getIntegerArrayList("ExamViewModel.answers")!!.toTypedArray()
        }
        this.currentQuestion = bundle.getInt("ExamViewModel.currentQuestion")
    }

}
