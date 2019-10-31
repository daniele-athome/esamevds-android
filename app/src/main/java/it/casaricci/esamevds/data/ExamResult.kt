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
class ExamResult(val numAnswers: Int, val numCorrect: Int, val numUnanswered: Int): Parcelable {

    private val maxWrongAnswers: Int
        get() = numAnswers / 10

    val numWrong: Int
        get() = numAnswers - numCorrect - numUnanswered

    val passed: Boolean
        get() = numCorrect >= (numAnswers - maxWrongAnswers)

    companion object {
        fun create(examData: ExamData, answers: Array<Int?>): ExamResult {
            var numCorrect = 0
            var numUnanswered = 0

            for (i in examData.questions.indices) {
                if (answers[i] == null) {
                    numUnanswered++
                }
                else if (examData.questions[i].correctAnswer == answers[i]) {
                    numCorrect++
                }
            }

            return ExamResult(answers.size, numCorrect, numUnanswered)
        }
    }

}
