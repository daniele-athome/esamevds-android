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
