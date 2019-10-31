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
