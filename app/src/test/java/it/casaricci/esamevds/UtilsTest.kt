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
