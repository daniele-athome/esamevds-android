package it.casaricci.esamevds.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamQuestion(val question: String,
                        val answers: Array<String>,
                        val correctAnswer: Int,
                        val picturePath: String?,
                        val subject: ExamSubject): Parcelable {

    override fun toString(): String {
        return question
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExamQuestion

        if (question != other.question) return false
        if (!answers.contentEquals(other.answers)) return false
        if (correctAnswer != other.correctAnswer) return false
        if (picturePath != other.picturePath) return false
        if (subject != other.subject) return false

        return true
    }

    override fun hashCode(): Int {
        var result = question.hashCode()
        result = 31 * result + answers.contentHashCode()
        result = 31 * result + correctAnswer
        result = 31 * result + (picturePath?.hashCode() ?: 0)
        result = 31 * result + subject.hashCode()
        return result
    }

}
