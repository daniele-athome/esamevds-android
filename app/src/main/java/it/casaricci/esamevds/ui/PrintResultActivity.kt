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

package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.print.PrintManager
import androidx.appcompat.app.AppCompatActivity
import it.casaricci.esamevds.R
import it.casaricci.esamevds.data.ExamData
import it.casaricci.esamevds.printer.ExamResultPrinter
import it.casaricci.esamevds.printer.MarkdownExamResultPrinter
import it.casaricci.esamevds.printer.MarkdownTextConverter
import kotlinx.android.synthetic.main.activity_print_result.*
import java.io.File
import java.io.FileOutputStream
import android.print.PrintAttributes

class PrintResultActivity : AppCompatActivity() {

    private lateinit var examData: ExamData
    private lateinit var examAnswers: Array<Int?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_result)

        examData = intent.getParcelableExtra(EXTRA_EXAM_DATA)!!
        examAnswers = intent.getIntegerArrayListExtra(EXTRA_ANSWERS)!!.toTypedArray()

        button_print.setOnClickListener {
            print()
        }

        renderDocument()
    }

    // FIXME blocking
    private fun renderDocument() {
        val basePath = File(cacheDir, "result")
        basePath.mkdirs()

        val printer: ExamResultPrinter = MarkdownExamResultPrinter()
        val markdownFile = File(basePath, "exam-result.md")
        val out = FileOutputStream(markdownFile)
        out.use {
            printer.printToStream(examData, examAnswers, it)
        }
        // write pictures
        for (question in examData.questions.filter { question -> question.picturePath != null }) {
            // setup child directories
            File(basePath, question.picturePath!!).parentFile!!.mkdirs()
            // copy image file
            val picOut = FileOutputStream(File(basePath, question.picturePath))
            val picIn = this::class.java.classLoader!!.getResourceAsStream(question.picturePath)
            picOut.use { itOut ->
                picIn.use { itIn ->
                    itIn.copyTo(itOut)
                }
            }
        }

        // convert Markdown to HTML
        val htmlFile = File(basePath, "exam-result.html")
        val converter = MarkdownTextConverter()
        // TODO i18n
        converter.convertMarkup("Risultati esame", markdownFile, htmlFile)
        document.loadUrl("file://${htmlFile}")
    }

    private fun print() {
        // minSdkVersion is 19 -- if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        val jobName = "${document.title} (${getString(R.string.app_name)})"
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

        val printAdapter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            document.createPrintDocumentAdapter(jobName)
        }
        else {
            document.createPrintDocumentAdapter()
        }

        val attrib = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
        printManager.print(jobName, printAdapter, attrib.build())
    }

    companion object {
        private const val EXTRA_EXAM_DATA = "examData"
        private const val EXTRA_ANSWERS = "answers"

        fun start(context: Context, examData: ExamData, answers: Array<Int?>) {
            val i = Intent(context, PrintResultActivity::class.java)
            i.putExtra(EXTRA_EXAM_DATA, examData)
            i.putIntegerArrayListExtra(EXTRA_ANSWERS, answers.toCollection(ArrayList()))
            context.startActivity(i)
        }
    }

}
