package dev.aylton.sitemap.views.editsite

import android.content.Intent
import android.graphics.Color
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dev.aylton.sitemap.helpers.showImagePicker
import dev.aylton.sitemap.models.Location
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import dev.aylton.sitemap.views.IMAGE_REQUEST
import dev.aylton.sitemap.views.LOCATION_REQUEST
import dev.aylton.sitemap.views.editlocation.EditLocationView
import org.jetbrains.anko.info

class EditSitePresenter(view: BaseView) : BasePresenter(view) {

    private lateinit var map: GoogleMap
    private val site: SiteModel = view.arguments?.getParcelable("site") ?: SiteModel()
    private val isEditMode = view.arguments!!.getBoolean("isEditMode")

    init {
        if (isEditMode)
            view.showSite(site)
        else
            view.showSite(SiteModel())
    }

    fun configureMap(m: GoogleMap) {
        map = m
        doUpdateMap()
    }

    private fun doUpdateMap() {
        map.clear()
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
        view?.showSite(site)
    }

    fun saveSite() {
        if (isEditMode) fireStore.update(site)
        else fireStore.create(site)
        view?.findNavController()?.popBackStack()
    }

    fun doSelectImage() {
        if (site.images.size < 4) showImagePicker(view!!, IMAGE_REQUEST)
        else view?.showSnackbar("You can only add up to 4 images", Color.RED)
    }

    fun doChangeLocation() {
        val intent = Intent(view?.context, EditLocationView::class.java)
        intent.putExtra("location", site.location)
        view?.startActivityForResult(intent, LOCATION_REQUEST)
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                val image = data.data.toString()
                if (!site.images.contains(image))
                    site.images.add(image)
                view?.showSite(site)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                site.location = location
                doUpdateMap()
            }
        }
    }

    fun updateName(name: String){
        site.name = name
    }

    fun updateDescription(description: String){
        site.description = description
    }
}