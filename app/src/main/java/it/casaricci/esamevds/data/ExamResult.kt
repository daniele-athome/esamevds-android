package it.casaricci.esamevds.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ExamResult(val numAnswers: Int, val numCorrect: Int, val numUnanswered: Int): Parcelable {

    private val maxWrongAnswers: Int
        get() = numAnswers / 10

    val numWrong: Int
        get() = numAnswers - numCorrect - numUnanswered

    val passed: Boolean
        get() = numCorrect >= (numAnswers - maxWrongAnswers)

    companion object {
        fun create(examData: ExamData, answers: Array<Int?>): ExamResult {
            var numCorrect = 0
            var numUnanswered = 0

            for (i in examData.questions.indices) {
                if (answers[i] == null) {
                    numUnanswered++
                }
                else if (examData.questions[i].correctAnswer == answers[i]) {
                    numCorrect++
                }
            }

            return ExamResult(answers.size, numCorrect, numUnanswered)
        }
    }

}
