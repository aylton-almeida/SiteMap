package dev.aylton.sitemap.views.editsite


import android.content.Intent
import android.os.Bundle
import android.view.*
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.synnapps.carouselview.ImageListener
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import dev.aylton.sitemap.views.IMAGE_PLACEHOLDER
import kotlinx.android.synthetic.main.fragment_edit_site.*
import kotlinx.android.synthetic.main.fragment_edit_site.carouselView
import kotlinx.android.synthetic.main.fragment_edit_site.mapView
import kotlinx.android.synthetic.main.fragment_edit_site.toolbar
import kotlinx.android.synthetic.main.fragment_site.*

class EditSiteView : BaseView() {

    private lateinit var presenter: EditSitePresenter
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_site, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(EditSitePresenter(this)) as EditSitePresenter

        init(toolbar, upEnabled = true, optionsMenu = true)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            presenter.populateMap(map)
        }

        btnAddImg.setOnClickListener { presenter.doSelectImage() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> presenter.saveSite()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null)
            presenter.doActivityResult(requestCode, resultCode, data)
    }

    override fun showSite(site: SiteModel) {
        val imageListener =
            ImageListener { position, imageView ->
                if (site.images.size == 0)
                    Glide
                        .with(this)
                        .load(IMAGE_PLACEHOLDER)
                        .centerCrop()
                        .into(imageView)
                else
                    Glide
                        .with(this)
                        .load(site.images[position])
                        .centerCrop()
                        .into(imageView)
            }

        carouselView.setImageListener(imageListener)
        if (site.images.size == 0)
            carouselView.pageCount = 1
        else
            carouselView.pageCount = site.images.size
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
