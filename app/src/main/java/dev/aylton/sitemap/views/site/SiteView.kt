package dev.aylton.sitemap.views.site


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.synnapps.carouselview.ImageListener
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_site.*

class SiteView : BaseView() {

    private lateinit var presenter: SitePresenter
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_site, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(SitePresenter(this)) as SitePresenter

        init(toolbar, upEnabled = true, optionsMenu = false, title = "Details")

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            presenter.populateMap(map)
        }

        fab.setOnClickListener { presenter.navigateEditSite() }
    }

    override fun showSite(site: SiteModel) {
        // Update Images
        val imageListener =
            ImageListener { position, imageView ->
                Glide
                    .with(this)
                    .load(site.images[position])
                    .centerCrop()
                    .into(imageView)
            }

        textName.text = site.name
        textDescription.text = site.description
        carouselView.setImageListener(imageListener)
        carouselView.pageCount = site.images.size

        // Update location
        textLat.text = String.format(resources.getString(R.string.latitude), site.location.lat)
        textLng.text = String.format(resources.getString(R.string.longitude), site.location.lng)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
}
