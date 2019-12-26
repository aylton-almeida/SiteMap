package dev.aylton.sitemap.views.site


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_site.*

class SiteView : BaseView() {

    private lateinit var presenter: SitePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_site, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(SitePresenter(this)) as SitePresenter

        init(toolbar, upEnabled = true, optionsMenu = false, title = "Details")
    }

    override fun showSite(site: SiteModel) {
        textName.text = site.name
        textDescription.text = site.description
        Glide.with(this).load(site.image).into(imageView)
    }

}
