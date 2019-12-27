package dev.aylton.sitemap.views.site

import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class SitePresenter(view: BaseView) : BasePresenter(view) {

    var site: SiteModel = view.arguments!!.getParcelable("site")!!

    init {
        view.showSite(site)
    }

    fun navigateEditSite() {
        view?.findNavController()?.navigate(R.id.action_siteView_to_editSiteView)
    }

    fun populateMap(map: GoogleMap) {
        map.uiSettings.isZoomControlsEnabled = false
        val loc = LatLng(site.location.getValue("lat"), site.location.getValue("lng"))
        val options = MarkerOptions().title(site.name).position(loc)
        map.addMarker(options)
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                loc,
                site.location.getValue("zoom").toFloat()
            )
        )
    }
}