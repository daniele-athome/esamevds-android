package it.casaricci.esamevds.persistence

import androidx.room.TypeConverter
import it.casaricci.esamevds.data.ExamSubject
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromSubjectNumber(value: Int?): ExamSubject {
        return ExamSubject[value ?: 0]!!
    }

    @TypeConverter
    fun examSubjectToNumber(subject: ExamSubject): Int {
        return subject.index
    }

}
