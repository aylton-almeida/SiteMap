package dev.aylton.sitemap.views.favorites


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.aylton.sitemap.R
import dev.aylton.sitemap.adapters.SiteListAdapter
import dev.aylton.sitemap.adapters.SiteListener
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_public_site_list.*

class FavouritesView : BaseView(), SiteListener {

    lateinit var presenter: FavouritesPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(FavouritesPresenter(this)) as FavouritesPresenter
    }

    override fun showSites(sites: List<SiteModel>) {
        recyclerView?.adapter =
            SiteListAdapter(sites, this)
    }

    override fun onSiteClick(site: SiteModel) {
        presenter.navigateSiteView(site)
    }

    override fun onSiteChecked(site: SiteModel, isChecked: Boolean) {
        presenter.setIsVisited(site, isChecked)
    }

}
