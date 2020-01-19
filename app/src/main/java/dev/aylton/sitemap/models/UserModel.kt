package dev.aylton.sitemap.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class UserModel(
    var id: String = "",
    var email: String = "",
    var visitedSites: ArrayList<VisitedSite> = ArrayList()
) : Parcelable

@Parcelize
data class VisitedSite(
    var id: String = "",
    var date: Date? = null
): Parcelable