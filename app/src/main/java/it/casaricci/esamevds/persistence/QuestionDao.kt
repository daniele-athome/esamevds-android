/*
 * EsameVDS Android app
 * Copyright (c) 2020 Daniele Ricci
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
