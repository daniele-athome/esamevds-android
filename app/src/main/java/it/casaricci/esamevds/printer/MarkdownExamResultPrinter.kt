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

package it.casaricci.esamevds.printer

import it.casaricci.esamevds.data.ExamData
import it.casaricci.esamevds.data.ExamResult
import java.io.OutputStream
import java.io.PrintWriter
import java.io.Writer
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MarkdownExamResultPrinter : ExamResultPrinter {

    private val dateFormatter: DateFormat = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY)

    override fun printToStream(examData: ExamData, answers: Array<Int?>, out: OutputStream) {
        return printToWriter(examData, answers, PrintWriter(out))
    }

    override fun printToWriter(examData: ExamData, answers: Array<Int?>, out: Writer) {
        val result = ExamResult.create(examData, answers)

        val writer = PrintWriter(out)
        writer.use { _writer -> with (_writer) {
            val date = today()
            val title = "Simulazione di esame del ${date}"
            println(title)
            println("=".repeat(title.length))
            println()

            val outcome = if (result.passed) "IDONEO" else "NON IDONEO"
            println("Esito: **${outcome}**  ")
            println("Risposte esatte: ${result.numCorrect}  ")
            println("Risposte errate: ${result.numWrong}  ")
            println("Non risposte: ${result.numUnanswered}  ")
            println()
            println("-----------------")
            println()

            for (q in examData.questions.indices) {
                println("*Domanda ${q + 1} di ${examData.questions.size}*  ")
                println(examData.questions[q].question)
                println()
                examData.questions[q].picturePath?.let {
                    // TODO picture in Markdown???
                    println("![figura](${it})")
                    println()
                }

                // a = risposta corrente
                // answers[q] = risposta data
                // correctAnswer = risposta corretta
                for (a in examData.questions[q].answers.indices) {
                    val answerFormat: String = when (a) {
                        examData.questions[q].correctAnswer ->
                            if (answers[q] == null)
                                "**${coloredSpan("orange")}**"
                            else
                                "**${coloredSpan("green")}**"
                        answers[q] -> "**${coloredSpan("red")}**"
                        else -> "%s"
                    }

                    println("* ${answerFormat.format(examData.questions[q].answers[a])}")
                }

                println()
                println("-----------------")
                println()
            }
        }}
    }

    private fun coloredSpan(color: String, content: String = "%s"): String {
        return "<span style=\"color: ${color}\">${content}</span>"
    }

    @Synchronized
    private fun today(): String = dateFormatter.format(Date())

}
