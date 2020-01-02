package dev.aylton.sitemap.views.editsite

import android.content.Intent
import android.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dev.aylton.sitemap.helpers.showImagePicker
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import dev.aylton.sitemap.views.IMAGE_REQUEST
import org.jetbrains.anko.info

class EditSitePresenter(view: BaseView) : BasePresenter(view) {

    private val site: SiteModel = view.arguments?.getParcelable("site") ?: SiteModel()
    private val isEditMode = view.arguments!!.getBoolean("isEditMode")

    init {
        if (isEditMode)
            view.showSite(site)
        else
            view.showSite(SiteModel())
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

    fun saveSite() {
        // TODO: Implement
    }

    fun doSelectImage() {
        if (site.images.size < 4)
            showImagePicker(view!!, IMAGE_REQUEST)
        else
            view?.showSnackbar("You can only add up to 4 images", Color.RED)
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                val image = data.data.toString()
                if (!site.images.contains(image))
                    site.images.add(image)
                view?.showSite(site)
            }
//            LOCATION_REQUEST -> {
//                val location = data.extras?.getParcelable<Location>("location")!!
//                placemark.location = location
//                locationUpdate(location)
//            }
        }
    }
}