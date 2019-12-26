package dev.aylton.sitemap.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var privateSites: ArrayList<SiteModel> = ArrayList(),
    var visitedSites: ArrayList<String> = ArrayList()
) : Parcelable