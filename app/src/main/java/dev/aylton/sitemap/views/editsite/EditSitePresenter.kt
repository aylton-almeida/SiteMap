package dev.aylton.sitemap.views.editsite

import android.content.Intent
import android.graphics.Color
import dev.aylton.sitemap.helpers.showImagePicker
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import dev.aylton.sitemap.views.IMAGE_REQUEST

class EditSitePresenter(view: BaseView) : BasePresenter(view) {

    private val site: SiteModel = view.arguments?.getParcelable("site") ?: SiteModel()
    val isEditMode = view.arguments!!.getBoolean("isEditMode")

    init {
        if (isEditMode)
            view.showSite(site)
        else
            view.showSite(SiteModel())
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