package it.casaricci.esamevds.persistence

import androidx.room.*
import java.util.*

@Dao
@TypeConverters(Converters::class)
interface ExamDao {

    @Query("SELECT * FROM exams")
    fun getAll(): List<Exam>

    @Query("SELECT * FROM exams WHERE id = :id")
    fun findById(id: Long): Exam?

    @Query("SELECT * FROM exams WHERE specialty = :specialty AND current_question > 0 ORDER BY last_access DESC LIMIT 1")
    fun findOngoing(specialty: Int): Exam?

    @Insert
    fun insert(exam: Exam): Long

    @Delete
    fun delete(exam: Exam)

    @Query("DELETE FROM exams WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("UPDATE exams set current_question = :currentQuestion, last_access = :lastAccess WHERE id = :id")
    fun updateExam(id: Long, currentQuestion: Int, lastAccess: Date): Int

}
