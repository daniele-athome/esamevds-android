package it.casaricci.esamevds.persistence

import androidx.room.*
import it.casaricci.esamevds.data.ExamSubject

@Entity(
    tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = Exam::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("exam_id"))
    ],
    primaryKeys = ["exam_id", "question_id"]
)
@TypeConverters(Converters::class)
data class Question(
    @ColumnInfo(name = "exam_id") val examId: Long,
    @ColumnInfo(name = "question_id") val questionId: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "answer1") val answer1: String?,
    @ColumnInfo(name = "answer2") val answer2: String?,
    @ColumnInfo(name = "answer3") val answer3: String?,
    @ColumnInfo(name = "answer4") val answer4: String?,
    @ColumnInfo(name = "picture") val picturePath: String?,
    @ColumnInfo(name = "correct") val correctAnswer: Int,
    @ColumnInfo(name = "given") val answer: Int?,
    @ColumnInfo(name = "subject") val subject: ExamSubject
)
