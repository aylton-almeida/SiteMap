package dev.aylton.sitemap.views.sitesMap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class SitesMapPresenter(view: BaseView) : BasePresenter(view) {

    fun populateMap(map: GoogleMap) {
        map.uiSettings.isZoomControlsEnabled = true
        val allSites: ArrayList<SiteModel> = arrayListOf()
        fireStore.publicSites.forEach { allSites.add(it.copy()) }
        fireStore.privateSites.forEach { allSites.add(it.copy()) }
        allSites.forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val iconColor = if (it.public) BitmapDescriptorFactory.HUE_RED else BitmapDescriptorFactory.HUE_AZURE
            val options = MarkerOptions()
                .title(it.name)
                .position(loc)
                .icon(BitmapDescriptorFactory.defaultMarker(iconColor))
            map.addMarker(options).tag = it
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    loc,
                    it.location.zoom
                )
            )
            view?.showSite(it)
        }
    }

    fun doOnMarkerClick(marker: Marker) {
        val site = marker.tag as SiteModel
        view?.showSite(site)
    }
}