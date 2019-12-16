package dev.aylton.sitemap.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SiteModel(
    val id: Long = 0,
    var name: String = "",
    var description: String = "",
    var image: String = ""
) : Parcelable