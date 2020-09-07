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
import com.airbnb.paris.extensions.style
import it.casaricci.esamevds.R
import it.casaricci.esamevds.Utils
import kotlinx.android.synthetic.main.fragment_ct_doa.*
import kotlinx.android.synthetic.main.fragment_ct_guess_degrees.canvas_compass
import java.util.*
import kotlin.math.abs

/**
 * "Direction of arrival" compass training game.
 */
class CTDoaFragment : Fragment(), CompassTrainingFragment, View.OnClickListener {

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

        arrayOf(
            answer_north_west, answer_north, answer_north_east,
            answer_west, answer_east,
            answer_south_west, answer_south, answer_south_east
        ).forEach { it.setOnClickListener(this) }
        loadQuestion()
    }

    override fun onClick(v: View?) {
        v?.let { view ->
            // cardinal point degrees are stored in button tag
            val cardinalPoint = Integer.parseInt(view.tag.toString())
            val answerDegrees = Utils.invertDirection(cardinalPoint)
            val answerDiff = Utils.floorMod(answerDegrees - canvas_compass.degrees + 180, 360) - 180

            if (abs(answerDiff) <= 30) {
                view.style(R.style.AppTheme_Button_CompassTraining_AnswerButton_Correct)
                // TODO correct answer
                MaterialDialog(view.context).show {
                    message(text="Corretto!")
                    positiveButton(android.R.string.ok) {
                        container?.onGameCompleted()
                    }
                }
            }
            else {
                view.style(R.style.AppTheme_Button_CompassTraining_AnswerButton_Wrong)
            }
        }
    }

    private fun resetAnswer() {
        arrayOf(
            answer_north_west, answer_north, answer_north_east,
            answer_west, answer_east,
            answer_south_west, answer_south, answer_south_east
        ).forEach { it.style(R.style.AppTheme_Button_CompassTraining_AnswerButton) }
    }

    private fun loadQuestion() {
        resetAnswer()
        canvas_compass.degrees = Random().nextInt(72) * 5
    }

}
