package dev.aylton.sitemap.views.editsite

import android.content.Intent
import dev.aylton.sitemap.helpers.showImagePicker
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import dev.aylton.sitemap.views.IMAGE_REQUEST

class EditSitePresenter(view: BaseView) : BasePresenter(view) {

    val site = SiteModel()

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                site.images.add(data.data.toString())
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