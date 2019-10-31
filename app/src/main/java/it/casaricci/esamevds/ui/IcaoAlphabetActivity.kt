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
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import it.casaricci.esamevds.R
import kotlinx.android.synthetic.main.activity_icao_alphabet.*

class IcaoAlphabetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icao_alphabet)

        list_alphabet.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.icao_alphabet))
    }

    companion object {

        fun start(context: Context) {
            val i = Intent(context, IcaoAlphabetActivity::class.java)
            context.startActivity(i)
        }
    }

}
