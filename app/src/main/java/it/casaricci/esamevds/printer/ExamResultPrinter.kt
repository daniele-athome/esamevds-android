package it.casaricci.esamevds.printer

import it.casaricci.esamevds.data.ExamData
import java.io.OutputStream
import java.io.Writer

interface ExamResultPrinter {

    fun printToWriter(examData: ExamData, answers: Array<Int?>, out: Writer)

    fun printToStream(examData: ExamData, answers: Array<Int?>, out: OutputStream)

}
