package dev.aylton.sitemap.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SiteModel(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var image: String = "",
    var isVisited: Boolean = false,
    var location: Map<String, Double> = mapOf("lat" to 0.0, "lng" to 0.0, "zoom" to 0.0)
) : Parcelable
