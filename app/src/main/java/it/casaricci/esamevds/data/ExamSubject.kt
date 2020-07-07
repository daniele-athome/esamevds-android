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

package it.casaricci.esamevds.data

enum class ExamSubject(val index: Int) {
    ALL(0),
    /** Aerodinamica */
    AERODYNAMICS(1),
    /** Meteorologia */
    METEOROLOGY(2),
    /** Tecnologia e prestazioni degli apparecchi VDS */
    TECHNOLOGY(3),
    /** Tecnica di pilotaggio */
    PILOTING(4),
    /** Operazioni ed atterraggi di emergenza */
    EMERGENCIES(5),
    /** Norme di circolazione ed elementi di fonia aeronautica */
    TRAFFIC(6),
    /** Navigazione aerea */
    NAVIGATION(7),
    /** Elementi di legislazione aeronautica */
    LEGISLATION(8),
    /** Sicurezza del volo */
    SECURITY(9);

    companion object {
        private val map = values().associateBy(ExamSubject::index)
        operator fun get(value: Int) = map[value]
    }
}
