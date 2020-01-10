package dev.aylton.sitemap.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SiteModel(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var images: ArrayList<String> = ArrayList(),
    var visited: Boolean = false,
    var public: Boolean = false,
    var userId: String = "",
    var location: Location = Location()
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 49.0030,
    var lng: Double = 12.0957,
    var zoom: Float = 15f
) : Parcelable
