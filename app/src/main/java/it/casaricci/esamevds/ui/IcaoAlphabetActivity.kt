package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import it.casaricci.esamevds.R
import kotlinx.android.synthetic.main.activity_icao_alphabet.*

class IcaoAlphabetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icao_alphabet)

        list_alphabet.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.icao_alphabet))
    }

    companion object {

        fun start(context: Context) {
            val i = Intent(context, IcaoAlphabetActivity::class.java)
            context.startActivity(i)
        }
    }

}
