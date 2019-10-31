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

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import it.casaricci.esamevds.BuildConfig
import it.casaricci.esamevds.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_icao_alphabet.setOnClickListener {
            IcaoAlphabetActivity.start(this)
        }

        text_version.text = getString(
            R.string.text_version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )
    }

    fun select(view: View) {
        val specialty = (view.tag as String).toInt()
        GameChoiceActivity.start(this, specialty)
    }
}
