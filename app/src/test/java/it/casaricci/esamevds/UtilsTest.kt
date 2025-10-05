package it.casaricci.esamevds

import org.junit.Test

import org.junit.Assert.*

class UtilsTest {

    @Test
    fun normalizeDegrees() {
        assertEquals(180, Utils.normalizeDegrees(360+180))
        assertEquals(330, Utils.normalizeDegrees(-30))
    }

    @Test
    fun invertDirection() {
        assertEquals(180, Utils.invertDirection(0))
        assertEquals(0, Utils.invertDirection(180))
        assertEquals(135, Utils.invertDirection(315))
    }

}
