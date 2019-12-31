package dev.aylton.sitemap.views.sitelist.publicsitelist

import android.os.Bundle
import android.view.*
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BaseView
import dev.aylton.sitemap.views.sitelist.SiteListAsapter
import dev.aylton.sitemap.views.sitelist.SiteListener
import kotlinx.android.synthetic.main.fragment_public_site_list.*

class PublicSiteListView : BaseView(),
    SiteListener {

    private lateinit var presenter: PublicSiteListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_public_site_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(
            PublicSiteListPresenter(
                this
            )
        ) as PublicSiteListPresenter
    }

    override fun showSites(sites: List<SiteModel>) {
        recyclerView?.adapter =
            SiteListAsapter(sites, this)
    }

    override fun onSiteClick(site: SiteModel) {
        presenter.navigateSiteView(site)
    }

    override fun onSiteChecked(site: SiteModel, isChecked: Boolean) {
        presenter.setIsVisited(site, isChecked)
    }

}
