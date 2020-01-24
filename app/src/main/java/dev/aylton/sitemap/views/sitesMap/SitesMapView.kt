package dev.aylton.sitemap.views.sitesMap


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_sites_map.*

class SitesMapView : BaseView(), GoogleMap.OnMarkerClickListener {

    private lateinit var presenter: SitesMapPresenter
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sites_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(SitesMapPresenter(this)) as SitesMapPresenter

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.populateMap(map)
        }
    }

    override fun showSite(site: SiteModel) {
        Glide
            .with(this)
            .load(site.images[0])
            .centerCrop()
            .into(imageViewSite)
        siteName.text = site.name
        valueLikes.text = site.getLikes().toString()
        valueDislikes.text = site.getDislikes().toString()
        textLat.text = site.location.lat.toString()
        textLng.text = site.location.lng.toString()
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

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doOnMarkerClick(marker)
        return false
    }
}
