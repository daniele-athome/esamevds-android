package it.casaricci.esamevds.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExamData(val specialty: Int, val questions: List<ExamQuestion>): Parcelable
