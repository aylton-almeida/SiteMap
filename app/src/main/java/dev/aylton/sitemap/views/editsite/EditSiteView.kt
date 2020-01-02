package dev.aylton.sitemap.views.editsite


import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import dev.aylton.sitemap.views.IMAGE_PLACEHOLDER
import kotlinx.android.synthetic.main.fragment_edit_site.*
import kotlinx.android.synthetic.main.fragment_edit_site.carouselView
import kotlinx.android.synthetic.main.fragment_edit_site.toolbar

class EditSiteView : BaseView() {

    private lateinit var presenter: EditSitePresenter

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

        init(toolbar, upEnabled = true, optionsMenu = false)

        btnAddImg.setOnClickListener { presenter.doSelectImage() }
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
}
