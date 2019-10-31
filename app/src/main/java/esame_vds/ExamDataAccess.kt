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
import it.casaricci.esamevds.data.ExamQuestion
import it.casaricci.esamevds.data.ExamSubject

/**
 * Class used to workaround package-private protection of exam data inside esame_VDS.jar.
 */
object ExamDataAccess {

    // TODO specialty should be an enum mapping to integers

    val numQuestions: Int
        get() = Esame_VDS.num_aero +
            Esame_VDS.num_meteo +
            Esame_VDS.num_tecno +
            Esame_VDS.num_pilot +
            Esame_VDS.num_emerg +
            Esame_VDS.num_norme +
            Esame_VDS.num_navig +
            Esame_VDS.num_legis +
            Esame_VDS.num_sicur

    /** Return exam data for a complete exam simulation or a specific subject. */
    fun getExamData(specialty: Int, subject: ExamSubject): ExamData {
        loadQuestions(specialty)
        val questionario = Questionario_esame(subject.index)
        return convertQuestions(questionario, specialty)
    }

    /** Return exam data for all questions. */
    // FIXME doesn't work because it's fixed to 70 questions
    fun getAllQuestions(specialty: Int): ExamData {
        loadQuestions(specialty)

        val oldNumDomande = Esame_VDS.num_domande_esame
        Esame_VDS.num_domande_esame = numQuestions

        val questionario = Questionario_esame(0)
        val examData = convertQuestions(questionario, specialty)

        Esame_VDS.num_domande_esame = oldNumDomande
        return examData
    }

    private fun convertQuestions(questionario: Questionario_esame, specialty: Int): ExamData {
        val questions = mutableListOf<ExamQuestion>()
        for (i in 0 until questionario._numero_domande) {
            val question = questionario.get_domanda(i).trim()
            val answers = arrayOfNulls<String>(questionario.get_numero_risposte(i))
            answers[0] = questionario.get_risposta_a(i).trim()
            if (answers.size > 1) {
                answers[1] = questionario.get_risposta_b(i).trim()
            }
            if (answers.size > 2) {
                answers[2] = questionario.get_risposta_c(i).trim()
            }
            if (answers.size > 3) {
                answers[3] = questionario.get_risposta_d(i).trim()
            }
            val correctAnswer = questionario.get_risposta_esatta(i)
            val picture = questionario.get_numero_figura(i)

            questions.add(ExamQuestion(question, answers as Array<String>, decodeAnswer(correctAnswer),
                getPicturePath(picture), getSubject(questionario, i)))
        }
        return ExamData(specialty, questions)
    }

    private fun getSubject(questionario: Questionario_esame, index: Int): ExamSubject {
        if (questionario._numero_materia > 0) {
            return ExamSubject[questionario._numero_materia]!!
        }

        if (index < Esame_VDS.m1_a) {
            return ExamSubject.AERODYNAMICS
        }
        else if (index >= Esame_VDS.m1_a && index < Esame_VDS.m2_a) {
            return ExamSubject.METEOROLOGY
        }
        else if (index >= Esame_VDS.m2_a && index < Esame_VDS.m3_a) {
            return ExamSubject.TECHNOLOGY
        }
        else if (index >= Esame_VDS.m3_a && index < Esame_VDS.m4_a) {
            return ExamSubject.PILOTING
        }
        else if (index >= Esame_VDS.m4_a && index < Esame_VDS.m5_a) {
            return ExamSubject.EMERGENCIES
        }
        else if (index >= Esame_VDS.m5_a && index < Esame_VDS.m6_a) {
            return ExamSubject.TRAFFIC
        }
        else if (index >= Esame_VDS.m6_a && index < Esame_VDS.m7_a) {
            return ExamSubject.NAVIGATION
        }
        else if (index >= Esame_VDS.m7_a && index < Esame_VDS.m8_a) {
            return ExamSubject.LEGISLATION
        }
        else if (index >= Esame_VDS.m8_a && index < Esame_VDS.m9_a) {
            return ExamSubject.SECURITY
        }

        throw IllegalArgumentException(index.toString())
    }

    private fun decodeAnswer(answer: Char): Int {
        return when (answer) {
            'A' -> 0
            'B' -> 1
            'C' -> 2
            'D' -> 3
            else -> throw IllegalArgumentException(answer.toString())
        }
    }

    private fun getPicturePath(index: Int): String? {
        if (index <= 0) return null
        return "esame_vds/foto/fig${index}.jpg"
    }

    private fun loadQuestions(specialty: Int) {
        Esame_VDS.carica_comuni()
        when (specialty) {
            0 -> Esame_VDS.carica_multiassi()
            1 -> Esame_VDS.carica_motoaliante()
            2 -> Esame_VDS.carica_paramotor()
            3 -> Esame_VDS.carica_autogiro()
            4 -> Esame_VDS.carica_elicottero()
            else -> throw IllegalArgumentException(specialty.toString())
        }
    }

}
