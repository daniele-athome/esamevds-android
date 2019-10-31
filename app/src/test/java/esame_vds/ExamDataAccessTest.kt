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

package esame_vds

import it.casaricci.esamevds.data.ExamData
import it.casaricci.esamevds.data.ExamSubject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.ComparisonFailure
import org.junit.Test

class ExamDataAccessTest {

    @Before
    fun setUp() {
    }

    private fun checkQuestions(examData: ExamData) {
        // no duplicates
        assertTrue(examData.questions.groupingBy { it }.eachCount().filter { it.value > 1 }.isEmpty())

        for (question in examData.questions) {
            //println(question)

            val arrayDomanda: Array<String>
            val arrayNumeroDomande: IntArray
            val arrayRispostaA: Array<String>
            val arrayRispostaB: Array<String>
            val arrayRispostaC: Array<String>
            val arrayRispostaD: Array<String>
            val arrayCorretta: CharArray

            when {
                question.subject == ExamSubject.AERODYNAMICS -> {
                    arrayDomanda = Esame_VDS.quiz1_domanda
                    arrayNumeroDomande = Esame_VDS.quiz1_numero_domande
                    arrayRispostaA = Esame_VDS.quiz1_a
                    arrayRispostaB = Esame_VDS.quiz1_b
                    arrayRispostaC = Esame_VDS.quiz1_c
                    arrayRispostaD = Esame_VDS.quiz1_d
                    arrayCorretta = Esame_VDS.quiz1_esatta
                }
                question.subject == ExamSubject.METEOROLOGY -> {
                    arrayDomanda = Esame_VDS.quiz2_domanda
                    arrayNumeroDomande = Esame_VDS.quiz2_numero_domande
                    arrayRispostaA = Esame_VDS.quiz2_a
                    arrayRispostaB = Esame_VDS.quiz2_b
                    arrayRispostaC = Esame_VDS.quiz2_c
                    arrayRispostaD = Esame_VDS.quiz2_d
                    arrayCorretta = Esame_VDS.quiz2_esatta
                }
                question.subject == ExamSubject.TECHNOLOGY -> {
                    arrayDomanda = Esame_VDS.quiz3_domanda
                    arrayNumeroDomande = Esame_VDS.quiz3_numero_domande
                    arrayRispostaA = Esame_VDS.quiz3_a
                    arrayRispostaB = Esame_VDS.quiz3_b
                    arrayRispostaC = Esame_VDS.quiz3_c
                    arrayRispostaD = Esame_VDS.quiz3_d
                    arrayCorretta = Esame_VDS.quiz3_esatta
                }
                question.subject == ExamSubject.PILOTING -> {
                    arrayDomanda = Esame_VDS.quiz4_domanda
                    arrayNumeroDomande = Esame_VDS.quiz4_numero_domande
                    arrayRispostaA = Esame_VDS.quiz4_a
                    arrayRispostaB = Esame_VDS.quiz4_b
                    arrayRispostaC = Esame_VDS.quiz4_c
                    arrayRispostaD = Esame_VDS.quiz4_d
                    arrayCorretta = Esame_VDS.quiz4_esatta
                }
                question.subject == ExamSubject.EMERGENCIES -> {
                    arrayDomanda = Esame_VDS.quiz5_domanda
                    arrayNumeroDomande = Esame_VDS.quiz5_numero_domande
                    arrayRispostaA = Esame_VDS.quiz5_a
                    arrayRispostaB = Esame_VDS.quiz5_b
                    arrayRispostaC = Esame_VDS.quiz5_c
                    arrayRispostaD = Esame_VDS.quiz5_d
                    arrayCorretta = Esame_VDS.quiz5_esatta
                }
                question.subject == ExamSubject.TRAFFIC -> {
                    arrayDomanda = Esame_VDS.quiz6_domanda
                    arrayNumeroDomande = Esame_VDS.quiz6_numero_domande
                    arrayRispostaA = Esame_VDS.quiz6_a
                    arrayRispostaB = Esame_VDS.quiz6_b
                    arrayRispostaC = Esame_VDS.quiz6_c
                    arrayRispostaD = Esame_VDS.quiz6_d
                    arrayCorretta = Esame_VDS.quiz6_esatta
                }
                question.subject == ExamSubject.NAVIGATION -> {
                    arrayDomanda = Esame_VDS.quiz7_domanda
                    arrayNumeroDomande = Esame_VDS.quiz7_numero_domande
                    arrayRispostaA = Esame_VDS.quiz7_a
                    arrayRispostaB = Esame_VDS.quiz7_b
                    arrayRispostaC = Esame_VDS.quiz7_c
                    arrayRispostaD = Esame_VDS.quiz7_d
                    arrayCorretta = Esame_VDS.quiz7_esatta
                }
                question.subject == ExamSubject.LEGISLATION -> {
                    arrayDomanda = Esame_VDS.quiz8_domanda
                    arrayNumeroDomande = Esame_VDS.quiz8_numero_domande
                    arrayRispostaA = Esame_VDS.quiz8_a
                    arrayRispostaB = Esame_VDS.quiz8_b
                    arrayRispostaC = Esame_VDS.quiz8_c
                    arrayRispostaD = Esame_VDS.quiz8_d
                    arrayCorretta = Esame_VDS.quiz8_esatta
                }
                question.subject == ExamSubject.SECURITY -> {
                    arrayDomanda = Esame_VDS.quiz9_domanda
                    arrayNumeroDomande = Esame_VDS.quiz9_numero_domande
                    arrayRispostaA = Esame_VDS.quiz9_a
                    arrayRispostaB = Esame_VDS.quiz9_b
                    arrayRispostaC = Esame_VDS.quiz9_c
                    arrayRispostaD = Esame_VDS.quiz9_d
                    arrayCorretta = Esame_VDS.quiz9_esatta
                }
                else -> throw IllegalArgumentException("Invalid subject: " + question.subject)
            }

            // crappy algorithm to catch the right question match
            var attemptIndex = 0
            while (attemptIndex < arrayDomanda.size) {
                val indexDomanda = arrayDomanda.withIndex().indexOfFirst { domanda ->
                    domanda.index >= attemptIndex && domanda.value.trim() == question.question
                }
                assertTrue(indexDomanda >= 0)

                try {
                    assertEquals(arrayNumeroDomande[indexDomanda], question.answers.size)
                    for (i in 0 until arrayNumeroDomande[indexDomanda]) {
                        assertEquals(
                            when (i) {
                                0 -> arrayRispostaA
                                1 -> arrayRispostaB
                                2 -> arrayRispostaC
                                3 -> arrayRispostaD
                                else -> throw IllegalArgumentException("Something went wrong")
                            }[indexDomanda].trim(), question.answers[i]
                        )
                    }

                    assertEquals(arrayCorretta[indexDomanda] - 'A', question.correctAnswer)
                    break
                }
                catch (e: ComparisonFailure) {
                    println("Matching question $attemptIndex failed, trying next question")
                    attemptIndex = indexDomanda + 1
                }
            }

            assertTrue(attemptIndex < arrayDomanda.size)
        }
    }

    @Test
    fun coherentQuestions_specialty0() {
        val examData = ExamDataAccess.getExamData(0, ExamSubject.ALL)
        assertEquals(70, examData.questions.size)
        assertEquals(10, examData.questions.filter { question -> question.subject == ExamSubject.AERODYNAMICS }.size)
        assertEquals(10, examData.questions.filter { question -> question.subject == ExamSubject.METEOROLOGY }.size)
        assertEquals(12, examData.questions.filter { question -> question.subject == ExamSubject.TECHNOLOGY }.size)
        assertEquals(12, examData.questions.filter { question -> question.subject == ExamSubject.PILOTING }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.EMERGENCIES }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.TRAFFIC }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.NAVIGATION }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.LEGISLATION }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.SECURITY }.size)
        checkQuestions(examData)
    }

    @Test
    fun coherentQuestions_specialty1() {
        val examData = ExamDataAccess.getExamData(1, ExamSubject.ALL)
        assertEquals(70, examData.questions.size)
        assertEquals(10, examData.questions.filter { question -> question.subject == ExamSubject.AERODYNAMICS }.size)
        assertEquals(10, examData.questions.filter { question -> question.subject == ExamSubject.METEOROLOGY }.size)
        assertEquals(12, examData.questions.filter { question -> question.subject == ExamSubject.TECHNOLOGY }.size)
        assertEquals(12, examData.questions.filter { question -> question.subject == ExamSubject.PILOTING }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.EMERGENCIES }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.TRAFFIC }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.NAVIGATION }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.LEGISLATION }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.SECURITY }.size)
        checkQuestions(examData)
    }

    @Test
    fun coherentQuestions_specialty2() {
        val examData = ExamDataAccess.getExamData(2, ExamSubject.ALL)
        assertEquals(50, examData.questions.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.AERODYNAMICS }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.METEOROLOGY }.size)
        assertEquals(9, examData.questions.filter { question -> question.subject == ExamSubject.TECHNOLOGY }.size)
        assertEquals(15, examData.questions.filter { question -> question.subject == ExamSubject.PILOTING }.size)
        assertEquals(3, examData.questions.filter { question -> question.subject == ExamSubject.EMERGENCIES }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.TRAFFIC }.size)
        assertEquals(3, examData.questions.filter { question -> question.subject == ExamSubject.NAVIGATION }.size)
        assertEquals(3, examData.questions.filter { question -> question.subject == ExamSubject.LEGISLATION }.size)
        assertEquals(5, examData.questions.filter { question -> question.subject == ExamSubject.SECURITY }.size)
        checkQuestions(examData)
    }

    @Test
    fun coherentQuestions_specialty3() {
        val examData = ExamDataAccess.getExamData(3, ExamSubject.ALL)
        assertEquals(70, examData.questions.size)
        assertEquals(10, examData.questions.filter { question -> question.subject == ExamSubject.AERODYNAMICS }.size)
        assertEquals(10, examData.questions.filter { question -> question.subject == ExamSubject.METEOROLOGY }.size)
        assertEquals(12, examData.questions.filter { question -> question.subject == ExamSubject.TECHNOLOGY }.size)
        assertEquals(12, examData.questions.filter { question -> question.subject == ExamSubject.PILOTING }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.EMERGENCIES }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.TRAFFIC }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.NAVIGATION }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.LEGISLATION }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.SECURITY }.size)
        checkQuestions(examData)
    }

    @Test
    fun coherentQuestions_specialty4() {
        val examData = ExamDataAccess.getExamData(4, ExamSubject.ALL)
        assertEquals(70, examData.questions.size)
        assertEquals(10, examData.questions.filter { question -> question.subject == ExamSubject.AERODYNAMICS }.size)
        assertEquals(10, examData.questions.filter { question -> question.subject == ExamSubject.METEOROLOGY }.size)
        assertEquals(12, examData.questions.filter { question -> question.subject == ExamSubject.TECHNOLOGY }.size)
        assertEquals(12, examData.questions.filter { question -> question.subject == ExamSubject.PILOTING }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.EMERGENCIES }.size)
        assertEquals(4, examData.questions.filter { question -> question.subject == ExamSubject.TRAFFIC }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.NAVIGATION }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.LEGISLATION }.size)
        assertEquals(6, examData.questions.filter { question -> question.subject == ExamSubject.SECURITY }.size)
        checkQuestions(examData)
    }

    @After
    fun tearDown() {
    }

}
