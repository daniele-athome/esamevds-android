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
