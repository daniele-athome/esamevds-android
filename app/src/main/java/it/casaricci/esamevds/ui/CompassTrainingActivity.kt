package it.casaricci.esamevds.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import it.casaricci.esamevds.R
import java.util.*

class CompassTrainingActivity : AppCompatActivity(), CompassTrainingContainer {

    private val factory: GameFactory = GameFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass_training)

        newGame()
    }

    override fun onGameCompleted() {
        newGame()
    }

    private fun newGame() {
        val f = factory.getInstance()
        f.container = this
        supportFragmentManager.beginTransaction()
            .replace(R.id.train_fragment, f as Fragment)
            .commit()
    }

    class GameFactory {

        fun getInstance() : CompassTrainingFragment {
            return when(Random().nextInt(NUM_GAMES)) {
                0 -> CTGuessDegreesFragment.newInstance()
                1 -> CTDoaFragment.newInstance()
                else -> throw RuntimeException("Impossible")
            }
        }

        companion object {
            private const val NUM_GAMES = 2
        }
    }

    companion object {

        fun start(context: Context) {
            val i = Intent(context, CompassTrainingActivity::class.java)
            context.startActivity(i)
        }
    }

}
