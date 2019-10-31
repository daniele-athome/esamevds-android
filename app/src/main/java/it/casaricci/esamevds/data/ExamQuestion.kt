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

package it.casaricci.esamevds.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
