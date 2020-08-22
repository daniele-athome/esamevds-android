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

package it.casaricci.esamevds.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import it.casaricci.esamevds.R
import kotlinx.android.synthetic.main.fragment_ct_compass.*

class CTCompassFragment : Fragment(), CompassTrainingFragment {

    companion object {
        fun newInstance(): CTCompassFragment {
            val f = CTCompassFragment()
            // TODO what here?
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ct_compass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO
        text_question.text = "Quanti gradi sono?"
        text_answer.text = ""
        answer_keypad.setListener(object : NumberKeyboardListener {
            override fun onNumberClicked(number: Int) {
                text_answer.text = text_answer.text.toString() + number.toString()
            }

            override fun onLeftAuxButtonClicked() {
                // TODO confirm answer
            }

            override fun onRightAuxButtonClicked() {
                text_answer.text = text_answer.text.subSequence(0, text_answer.text.length - 1)
            }

        })
    }

}
