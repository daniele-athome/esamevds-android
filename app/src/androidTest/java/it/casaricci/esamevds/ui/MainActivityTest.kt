package it.casaricci.esamevds.ui

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import it.casaricci.esamevds.R

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule: IntentsTestRule<MainActivity>
        = IntentsTestRule(MainActivity::class.java)

    @Test
    fun openIcaoAlphabet() {
        onView(withId(R.id.button_icao_alphabet)).perform(click())
        intended(hasComponent(IcaoAlphabetActivity::class.java.name))
    }

    @Test
    fun openGameChoice_specialty0() {
        onView(withId(R.id.button1)).perform(click())
        intended(hasComponent(GameChoiceActivity::class.java.name))
    }

    @Test
    fun openGameChoice_specialty1() {
        onView(withId(R.id.button2)).perform(click())
        intended(hasComponent(GameChoiceActivity::class.java.name))
    }

    @Test
    fun openGameChoice_specialty2() {
        onView(withId(R.id.button3)).perform(click())
        intended(hasComponent(GameChoiceActivity::class.java.name))
    }

    @Test
    fun openGameChoice_specialty3() {
        onView(withId(R.id.button4)).perform(click())
        intended(hasComponent(GameChoiceActivity::class.java.name))
    }

    @Test
    fun openGameChoice_specialty4() {
        onView(withId(R.id.button5)).perform(click())
        intended(hasComponent(GameChoiceActivity::class.java.name))
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("it.casaricci.esamevds", appContext.packageName)
    }
}
