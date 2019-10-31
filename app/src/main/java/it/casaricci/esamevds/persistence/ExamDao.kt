/*
 * EsameVDS Android app
 * Copyright (c) 2019 Daniele Ricci
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
