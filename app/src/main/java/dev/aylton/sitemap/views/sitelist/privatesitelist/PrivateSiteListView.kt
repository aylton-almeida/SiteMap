package dev.aylton.sitemap.views.sitelist.privatesitelist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import dev.aylton.sitemap.views.sitelist.SiteListAdapter
import dev.aylton.sitemap.views.sitelist.SiteListener
import kotlinx.android.synthetic.main.fragment_private_site_list.recyclerView

class PrivateSiteListView : BaseView(), SiteListener {

    lateinit var presenter: PrivateSiteListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_private_site_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(PrivateSiteListPresenter(this)) as PrivateSiteListPresenter
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
