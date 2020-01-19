package dev.aylton.sitemap.views.editsite

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_edit_site.*

class EditSiteView : BaseView() {

    private lateinit var presenter: EditSitePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_site, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(EditSitePresenter(this)) as EditSitePresenter

        init(toolbar, upEnabled = true, optionsMenu = false, title = if (presenter.isEditMode) "Edit Site" else "Create Site")

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            presenter.configureMap(it)
        }

        btnAddImg.setOnClickListener { presenter.doSelectImage() }

        btnChangeLocation.setOnClickListener { presenter.doChangeLocation() }

        fab.setOnClickListener { presenter.saveSite() }

        fab.hide()

        inputName.addTextChangedListener {
            presenter.updateName(it.toString())
            validateForm()
        }

        inputDescription.addTextChangedListener {
            presenter.updateDescription(it.toString())
            validateForm()
        }
    }

    private fun validateForm() {
        if (inputName.text!!.isNotEmpty() && inputDescription.text!!.isNotEmpty() && presenter.getSiteImages().size > 0)
            fab.show()
        else
            fab.hide()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null)
            presenter.doActivityResult(requestCode, resultCode, data)
    }

    override fun showSite(site: SiteModel) {
        validateForm()
        // Update fields
        inputName.setText(site.name)
        inputDescription.setText(site.description)

        // Update images
        val imageListener =
            ImageListener { position, imageView ->
                if (site.images.size == 0)
                    Glide
                        .with(this)
                        .load(ContextCompat.getDrawable(context!!, R.drawable.image_placeholder))
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
