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
