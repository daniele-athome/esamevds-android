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
