package it.casaricci.esamevds.printer

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class MarkdownTextConverter {

    // TODO extensions?
    private val flexmarkParser = Parser.builder().build()
    // TODO extensions?
    private val flexmarkRenderer = HtmlRenderer.builder().build()

    fun convertMarkup(title: String, inputFile: File, outputFile: File) {
        val reader = FileReader(inputFile)
        reader.use { inStream ->
            val writer = FileWriter(outputFile)
            writer.use { outStream ->
                outStream.write(HTML_START.format(title))
                val doc = flexmarkParser.parseReader(inStream)
                flexmarkRenderer.render(doc, outStream)
                outStream.write(HTML_END)
            }
        }
    }

    companion object {
        private const val HTML_START = "<!DOCTYPE html><html lang=\"it-it\"><head>" +
            "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">" +
            "<meta charset=\"utf-8\">" +
            "<meta name=\"viewport\" content=\"width=device-width,minimum-scale=1\">" +
            // TODO css
            "<title>%s</title>" +
            "</head>" +
            "<body>"

        private const val HTML_END = "</body></html>"
    }

}
