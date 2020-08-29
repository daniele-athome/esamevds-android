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
