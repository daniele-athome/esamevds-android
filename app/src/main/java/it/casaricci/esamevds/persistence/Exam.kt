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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "exams")
@TypeConverters(Converters::class)
data class Exam(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "specialty") val specialty: Int,
    @ColumnInfo(name = "last_access") val lastAccess: Date,
    /** Current question. When > 0 (index starts from 1) the exam is still in progress. */
    @ColumnInfo(name = "current_question") val currentQuestion: Int
)
