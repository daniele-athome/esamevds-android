package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import it.casaricci.esamevds.R
import it.casaricci.esamevds.databinding.ActivityIcaoAlphabetBinding

class IcaoAlphabetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIcaoAlphabetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIcaoAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listAlphabet.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.icao_alphabet))
    }

    companion object {

        fun start(context: Context) {
            val i = Intent(context, IcaoAlphabetActivity::class.java)
            context.startActivity(i)
        }
    }

}
