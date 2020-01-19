package dev.aylton.sitemap.views.site

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.models.VisitedSite
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_site.*
import java.util.*

class SitePresenter(view: BaseView) : BasePresenter(view) {

    var site: SiteModel = view.arguments!!.getParcelable("site")!!

    init {
        view.speedDial.visibility = if (site.userId.isEmpty()) View.INVISIBLE else View.VISIBLE
        view.showSite(site)
    }

    fun navigateEditSite() {
        val isSitePublic = site.userId == ""
        view?.findNavController()?.navigate(R.id.action_siteView_to_editSiteView, bundleOf("isEditMode" to true, "site" to site, "isSitePublic" to isSitePublic))
    }

    fun deleteSite(){
        fireStore.delete(site)
        view?.findNavController()?.popBackStack()
    }

    fun populateMap(map: GoogleMap) {
        map.uiSettings.isZoomControlsEnabled = false
        val loc = LatLng(site.location.lat, site.location.lng)
        val options = MarkerOptions().title(site.name).position(loc)
        map.addMarker(options)
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                loc,
                site.location.zoom
            )
        )
    }
}