package it.casaricci.esamevds.printer

import esame_vds.ExamDataAccess
import it.casaricci.esamevds.data.ExamSubject
import org.junit.After
import org.junit.Before
import org.junit.Test

class MarkdownExamResultPrinterTest {

    private lateinit var printer: MarkdownExamResultPrinter

    @Before
    fun setUp() {
        printer = MarkdownExamResultPrinter()
    }

    @After
    fun tearDown() {
    }

    /** Not really a test... */
    @Test
    fun justPrintOut() {
        val examData = ExamDataAccess.getExamData(0, ExamSubject.ALL)
        val answers = arrayOfNulls<Int>(examData.questions.size)
        answers[0] = 1
        printer.printToStream(examData, answers, System.out)
    }

}
