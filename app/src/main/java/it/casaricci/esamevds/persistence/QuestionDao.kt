package it.casaricci.esamevds.persistence

import androidx.room.*

@Dao
@TypeConverters(Converters::class)
interface QuestionDao {

    @Query("SELECT * FROM questions")
    fun getAll(): List<Question>

    @Query("SELECT * FROM questions WHERE exam_id = :examId AND question_id = :questionId")
    fun findById(examId: Long, questionId: Int): Question?

    @Query("SELECT * FROM questions WHERE exam_id = :examId")
    fun findForExam(examId: Long): List<Question>

    @Insert
    fun insertAll(vararg questions: Question)

    @Delete
    fun delete(question: Question)

    @Query("DELETE FROM questions WHERE exam_id = :examId")
    fun deleteForExam(examId: Long)

    @Query("UPDATE questions set given = :answer WHERE exam_id = :examId AND question_id = :questionId")
    fun updateQuestion(examId: Long, questionId: Int, answer: Int?): Int

}
