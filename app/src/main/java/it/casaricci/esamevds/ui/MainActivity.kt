package it.casaricci.esamevds.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import it.casaricci.esamevds.BuildConfig
import it.casaricci.esamevds.R
import it.casaricci.esamevds.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCompassTraining.setOnClickListener {
            CompassTrainingActivity.start(this)
        }
        binding.buttonIcaoAlphabet.setOnClickListener {
            IcaoAlphabetActivity.start(this)
        }

        binding.textVersion.text = getString(
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
