package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.print.PrintManager
import androidx.appcompat.app.AppCompatActivity
import it.casaricci.esamevds.R
import it.casaricci.esamevds.data.ExamData
import it.casaricci.esamevds.printer.ExamResultPrinter
import it.casaricci.esamevds.printer.MarkdownExamResultPrinter
import it.casaricci.esamevds.printer.MarkdownTextConverter
import java.io.File
import java.io.FileOutputStream
import android.print.PrintAttributes
import androidx.core.content.IntentCompat
import it.casaricci.esamevds.databinding.ActivityPrintResultBinding

class PrintResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrintResultBinding

    private lateinit var examData: ExamData
    private lateinit var examAnswers: Array<Int?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrintResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        examData = IntentCompat.getParcelableExtra(intent, EXTRA_EXAM_DATA, ExamData::class.java)!!
        examAnswers = intent.getIntegerArrayListExtra(EXTRA_ANSWERS)!!.toTypedArray()

        binding.buttonPrint.setOnClickListener {
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
        binding.document.loadUrl("file://${htmlFile}")
    }

    private fun print() {
        val jobName = "${binding.document.title} (${getString(R.string.app_name)})"
        val printManager = getSystemService(PRINT_SERVICE) as PrintManager

        val printAdapter =
            binding.document.createPrintDocumentAdapter(jobName)

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
