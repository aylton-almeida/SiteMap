package dev.aylton.sitemap.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class SiteModel(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var images: ArrayList<String> = ArrayList(),
    var visited: Boolean = false,
    var visitedDate: Date = Date(),
    var public: Boolean = false,
    var userId: String = "",
    var location: Location = Location(),
    var notes: ArrayList<Note> = ArrayList()
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 49.0030,
    var lng: Double = 12.0957,
    var zoom: Float = 15f
) : Parcelable

@Parcelize
data class Note(
    var userId: String = "",
    var userEmail: String = "",
    var description: String = ""
): Parcelable