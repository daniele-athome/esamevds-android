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

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.paris.extensions.style
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import it.casaricci.esamevds.R
import kotlinx.android.synthetic.main.fragment_ct_guess_degrees.*
import java.lang.Exception
import java.util.*

/**
 * "Direction of arrival" compass training game.
 */
class CTDoaFragment : Fragment(), CompassTrainingFragment {

    companion object {
        fun newInstance(): CTDoaFragment {
            return CTDoaFragment()
        }
    }

    override var container: CompassTrainingContainer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ct_doa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO
        loadQuestion()
    }

    private fun resetAnswer() {
        // TODO
    }

    private fun loadQuestion() {
        resetAnswer()
        canvas_compass.degrees = Random().nextInt(72) * 5
    }

}
