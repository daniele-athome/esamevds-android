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
import com.afollestad.materialdialogs.MaterialDialog
import it.casaricci.esamevds.R
import kotlinx.android.synthetic.main.fragment_ct_doa.*
import kotlinx.android.synthetic.main.fragment_ct_guess_degrees.canvas_compass
import java.util.*
import kotlin.collections.HashMap

/**
 * "Direction of arrival" compass training game.
 */
class CTDoaFragment : Fragment(), CompassTrainingFragment, View.OnClickListener {

    companion object {
        val DOA_DEGREES = HashMap<String, Pair<Int, Int>>()

        init {
            // 30 degrees tolerance
            DOA_DEGREES["NW"] = Pair(135-30, 135+30)
            DOA_DEGREES["N"] = Pair(180-30, 180+30)
            DOA_DEGREES["NE"] = Pair(225-30, 225+30)
            DOA_DEGREES["W"] = Pair(90-30, 90+30)
            DOA_DEGREES["E"] = Pair(270-30, 270+30)
            DOA_DEGREES["SW"] = Pair(45-30, 45+30)
            // FIXME this doesn't work because of the zero threshold
            DOA_DEGREES["S"] = Pair(360-30, 0+30)
            DOA_DEGREES["SE"] = Pair(315-30, 315+30)
        }

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

        arrayOf(
            answer_north_west, answer_north, answer_north_east,
            answer_west, answer_east,
            answer_south_west, answer_south, answer_south_east
        ).forEach { it.setOnClickListener(this) }
        loadQuestion()
    }

    override fun onClick(v: View?) {
        v?.let {
            DOA_DEGREES[it.tag]?.let { answer ->
                if (canvas_compass.degrees >= answer.first && canvas_compass.degrees <= answer.second) {
                    // TODO correct answer
                    MaterialDialog(it.context).show {
                        message(text="Corretto!")
                        positiveButton(android.R.string.ok) {
                            container?.onGameCompleted()
                        }
                    }
                }
            }
        }
    }

    private fun resetAnswer() {
        // TODO
    }

    private fun loadQuestion() {
        resetAnswer()
        canvas_compass.degrees = Random().nextInt(72) * 5
    }

}
