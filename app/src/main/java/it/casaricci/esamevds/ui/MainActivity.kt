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
