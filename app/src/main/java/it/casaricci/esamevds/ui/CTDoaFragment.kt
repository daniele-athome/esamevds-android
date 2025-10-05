package it.casaricci.esamevds.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.paris.extensions.style
import it.casaricci.esamevds.R
import it.casaricci.esamevds.Utils
import it.casaricci.esamevds.databinding.FragmentCtDoaBinding
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

    private var _binding: FragmentCtDoaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCtDoaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allAnswers().forEach { it.setOnClickListener(this) }
        loadQuestion()
    }

    override fun onClick(v: View?) {
        v?.let { view ->
            if (!view.isClickable) {
                return
            }

            // cardinal point degrees are stored in button tag
            val cardinalPoint = Integer.parseInt(view.tag.toString())
            val answerDegrees = Utils.invertDirection(cardinalPoint)
            val answerDiff = Utils.floorMod(answerDegrees - binding.canvasCompass.degrees + 180, 360) - 180

            if (abs(answerDiff) <= 30) {
                setAnswerCorrect(view)
                binding.root.handler.postDelayed({
                    try {
                        container?.onGameCompleted()
                    }
                    catch (_: Exception) {
                    }
                }, 1000)
            }
            else {
                setAnswerWrong(view)
            }
        }
    }

    private fun setAnswerCorrect(view: View) {
        view.style(R.style.AppTheme_Button_CompassTraining_AnswerButton_Correct)
        allAnswers().forEach { it.isClickable = false }
    }

    private fun setAnswerWrong(view: View) {
        view.style(R.style.AppTheme_Button_CompassTraining_AnswerButton_Wrong)
    }

    private fun allAnswers(): Array<View> {
        return arrayOf(
            binding.answerNorthWest, binding.answerNorth, binding.answerNorthEast,
            binding.answerWest, binding.answerEast,
            binding.answerSouthWest, binding.answerSouth, binding.answerSouthEast
        )
    }

    private fun resetAnswer() {
        allAnswers().forEach {
            it.style(R.style.AppTheme_Button_CompassTraining_AnswerButton)
            it.isClickable = true
        }
    }

    private fun loadQuestion() {
        resetAnswer()
        binding.canvasCompass.degrees = Random().nextInt(72) * 5
    }

}
