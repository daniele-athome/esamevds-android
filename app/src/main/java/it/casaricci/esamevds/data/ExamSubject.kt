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
