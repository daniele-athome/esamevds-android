package it.casaricci.esamevds.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.paris.extensions.style
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import it.casaricci.esamevds.R
import it.casaricci.esamevds.databinding.FragmentCtGuessDegreesBinding
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

    override var container: CompassTrainingContainer? = null

    private var _binding: FragmentCtGuessDegreesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCtGuessDegreesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.answerKeypad.setListener(object : NumberKeyboardListener {
            @SuppressLint("SetTextI18n")
            override fun onNumberClicked(number: Int) {
                if (isWaitingCorrectAnswerAnimation()) {
                    return
                }
                if (binding.textAnswer.tag != null && binding.textAnswer.tag == false) {
                    resetAnswer()
                }
                if (binding.textAnswer.length() < 3) {
                    binding.textAnswer.text = binding.textAnswer.text.toString() + number.toString()
                }
            }

            override fun onLeftAuxButtonClicked() {
                // waiting for correct answer animation
                if (isWaitingCorrectAnswerAnimation() || binding.textAnswer.text.isEmpty()) {
                    return
                }

                if (binding.textAnswer.text == binding.canvasCompass.degrees.toString()) {
                    setAnswerCorrect()
                    binding.root.handler.postDelayed({
                        try {
                            container?.onGameCompleted()
                        }
                        catch (_: Exception) {
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
        return binding.textAnswer.tag != null && binding.textAnswer.tag == true
    }

    private fun setAnswerCorrect() {
        binding.textAnswer.style(R.style.TextAppearance_CompassTraining_AnswerText_Correct)
        binding.textAnswer.hint = ""
        binding.textAnswer.tag = true
    }

    private fun setAnswerWrong() {
        binding.textAnswer.style(R.style.TextAppearance_CompassTraining_AnswerText_Wrong)
        binding.textAnswer.hint = ""
        binding.textAnswer.tag = false
    }

    private fun resetAnswer() {
        binding.textAnswer.style(R.style.TextAppearance_CompassTraining_AnswerText)
        binding.textAnswer.tag = null
        binding.textAnswer.text = ""
        binding.textAnswer.hint = getString(R.string.ct_guess_degrees_answer_hint)
    }

    private fun loadQuestion() {
        resetAnswer()
        binding.canvasCompass.degrees = Random().nextInt(72) * 5
    }

}
