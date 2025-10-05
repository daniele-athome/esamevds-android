package it.casaricci.esamevds.ui

import android.content.Context
import android.os.Bundle
import esame_vds.ExamDataAccess
import it.casaricci.esamevds.data.ExamData
import it.casaricci.esamevds.data.ExamQuestion
import it.casaricci.esamevds.data.ExamSubject
import it.casaricci.esamevds.persistence.AppDatabase
import it.casaricci.esamevds.persistence.ExamRepository

class ExamViewModel(context: Context) {

    private val repository: ExamRepository = ExamRepository(AppDatabase(context.applicationContext))

    var examId: Long? = null

    var examData: ExamData? = null
        private set(value) {
            field = value
            answers = if (value != null) arrayOfNulls(value.questions.size) else null
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
        repository.updateExamProgress(this.examId!!, currentQuestion)
    }

    fun previousQuestion() {
        currentQuestion = currentQuestion.dec().coerceAtLeast(0)
        repository.updateExamProgress(this.examId!!, currentQuestion)
    }

    fun storeAnswer(answer: Int?) {
        answers?.set(currentQuestion, answer)
        repository.updateExam(this.examId!!, currentQuestion, answer)
    }

    fun cancel() {
        repository.cancelExam(this.examId!!)
    }

    fun terminate() {
        repository.endExam(this.examId!!)
    }

    fun start(specialty: Int) {
        val thisExamData = ExamDataAccess.getExamData(specialty, ExamSubject.ALL)
        this.examData = thisExamData
        this.examId = repository.startExam(thisExamData)
    }

    fun resume(examId: Long) {
        val exam = repository.loadExam(examId)!!
        this.examData = exam.examData
        this.answers = exam.answers
        this.currentQuestion = exam.currentQuestion
        this.examId = examId
    }

    fun onSaveInstanceState(bundle: Bundle) {
        this.examId?.let { bundle.putLong("ExamViewModel.examId", it) }
        this.examData?.let { bundle.putParcelable("ExamViewModel.examData", it) }
        this.answers?.let { bundle.putIntegerArrayList("ExamViewModel.answers", ArrayList(it.toList())) }
        bundle.putInt("ExamViewModel.currentQuestion", this.currentQuestion)
    }

    fun onRestoreInstanceState(bundle: Bundle) {
        if (bundle.containsKey("ExamViewModel.examId")) {
            this.examId = bundle.getLong("ExamViewModel.examId")
        }
        if (bundle.containsKey("ExamViewModel.examData")) {
            this.examData = bundle.getParcelable("ExamViewModel.examData")
        }
        if (bundle.containsKey("ExamViewModel.answers")) {
            this.answers = bundle.getIntegerArrayList("ExamViewModel.answers")!!.toTypedArray()
        }
        this.currentQuestion = bundle.getInt("ExamViewModel.currentQuestion")
    }

}
