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
import kotlinx.android.synthetic.main.fragment_ct_compass.*
import java.lang.Exception
import java.util.*

/**
 * "Guess the degrees" compass training game.
 */
class CTGuessDegreesFragment : Fragment(), CompassTrainingFragment {

    companion object {
        fun newInstance(): CTGuessDegreesFragment {
            return CTGuessDegreesFragment()
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
        answer_keypad.setListener(object : NumberKeyboardListener {
            @SuppressLint("SetTextI18n")
            override fun onNumberClicked(number: Int) {
                if (isWaitingCorrectAnswerAnimation()) {
                    return
                }
                if (text_answer.tag != null && text_answer.tag == false) {
                    resetAnswer()
                }
                if (text_answer.length() < 3) {
                    text_answer.text = text_answer.text.toString() + number.toString()
                }
            }

            override fun onLeftAuxButtonClicked() {
                // waiting for correct answer animation
                if (isWaitingCorrectAnswerAnimation() || text_answer.text.isEmpty()) {
                    return
                }

                if (text_answer.text == canvas_compass.degrees.toString()) {
                    setAnswerCorrect()
                    Handler().postDelayed({
                        try {
                            loadQuestion()
                        }
                        catch (e: Exception) {
                        }
                    }, 1000)
                }
                else {
                    setAnswerWrong()
                }
            }

            override fun onRightAuxButtonClicked() {
                // waiting for correct answer animation
                if (isWaitingCorrectAnswerAnimation()) {
                    return
                }

                resetAnswer()
            }
        })

        loadQuestion()
    }

    private fun isWaitingCorrectAnswerAnimation(): Boolean {
        return text_answer.tag != null && text_answer.tag == true
    }

    private fun setAnswerCorrect() {
        text_answer.style(R.style.TextAppearance_CompassTraining_AnswerText_Correct)
        text_answer.hint = ""
        text_answer.tag = true
    }

    private fun setAnswerWrong() {
        text_answer.style(R.style.TextAppearance_CompassTraining_AnswerText_Wrong)
        text_answer.hint = ""
        text_answer.tag = false
    }

    private fun resetAnswer() {
        text_answer.style(R.style.TextAppearance_CompassTraining_AnswerText)
        text_answer.tag = null
        text_answer.text = ""
        text_answer.hint = getString(R.string.ct_guess_degrees_answer_hint)
    }

    private fun loadQuestion() {
        resetAnswer()
        canvas_compass.degrees = Random().nextInt(72) * 5
    }

}
