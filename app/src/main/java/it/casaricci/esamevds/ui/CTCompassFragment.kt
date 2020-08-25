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
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import it.casaricci.esamevds.R
import kotlinx.android.synthetic.main.fragment_ct_compass.*
import java.lang.Exception
import java.util.*

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
        answer_keypad.setListener(object : NumberKeyboardListener {
            override fun onNumberClicked(number: Int) {
                if (text_answer.tag != null) {
                    resetAnswer()
                }
                text_answer.text = text_answer.text.toString() + number.toString()
            }

            override fun onLeftAuxButtonClicked() {
                if (text_answer.text == canvas_compass.degrees.toString()) {
                    setAnswerCorrect()
                    // TODO disable keypad input while waiting for this runnable
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
                resetAnswer()
            }

        })

        loadQuestion()
    }

    // TODO green/red badges here instead of text styles
    // https://github.com/airbnb/paris

    private fun setAnswerCorrect() {
        TextViewCompat.setTextAppearance(text_answer,
            R.style.TextAppearance_CompassTraining_AnswerText_Correct)
        text_answer.tag = true
    }

    private fun setAnswerWrong() {
        TextViewCompat.setTextAppearance(text_answer,
            R.style.TextAppearance_CompassTraining_AnswerText_Wrong)
        text_answer.tag = false
    }

    private fun resetAnswer() {
        TextViewCompat.setTextAppearance(text_answer,
            R.style.TextAppearance_CompassTraining_AnswerText)
        text_answer.tag = null
        text_answer.text = ""
    }

    private fun loadQuestion() {
        resetAnswer()
        text_question.text = "Quanti gradi sono?"
        canvas_compass.degrees = Random().nextInt(72) * 5
    }

}
