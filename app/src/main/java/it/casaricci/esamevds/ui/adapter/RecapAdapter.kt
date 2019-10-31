package it.casaricci.esamevds.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import it.casaricci.esamevds.R
import it.casaricci.esamevds.data.ExamQuestionResult
import it.casaricci.esamevds.ui.view.RecapItemView

class RecapAdapter(context: Context) : ArrayAdapter<ExamQuestionResult>(context, 0) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: RecapItemView = if (convertView == null) {
            inflater.inflate(R.layout.item_recap, parent, false) as RecapItemView
        }
        else {
            convertView as RecapItemView
        }

        val item = getItem(position)
        item?.let {
            view.bind(it.question, it.answer)
        }

        return view
    }

}
