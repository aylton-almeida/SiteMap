package dev.aylton.sitemap.views.site

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.Rating
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.models.UserModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_site.*

class SitePresenter(view: BaseView) : BasePresenter(view) {

    var site: SiteModel = view.arguments!!.getParcelable("site")!!

    init {
        view.speedDial.visibility = if (site.userId.isEmpty()) View.INVISIBLE else View.VISIBLE
        view.showSiteWithUser(site, fireStore.user)
    }

    fun navigateEditSite() {
        val isSitePublic = site.userId == ""
        view?.findNavController()?.navigate(R.id.action_site_dest_to_editSite_dest, bundleOf("isEditMode" to true, "site" to site, "isSitePublic" to isSitePublic))
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

    fun navigateToNotes(){
        view!!.findNavController().navigate(R.id.action_site_dest_to_notes_dest, bundleOf("site" to site))
    }

    fun addRating(positive: Boolean) {
        site.rating.find { it.positive == !positive && it.userId == fireStore.user.id }.let {
            site.rating.remove(it)
        }
        site.rating.add(Rating(positive, fireStore.user.id))
        fireStore.update(site)
        view?.showSiteWithUser(site, fireStore.user)
    }

    fun removeRating(positive: Boolean) {
        site.rating.remove(site.rating.find { it.userId == fireStore.user.id && it.positive == positive})
        fireStore.update(site)
        view?.showSiteWithUser(site, fireStore.user)
    }
}