package dev.aylton.sitemap.views.sitelist.publicsitelist

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class PublicSiteListPresenter(view: BaseView) : BasePresenter(view) {

    init {
        fireStore.fetchSites({loadSites()}, true)
    }

    private fun loadSites() {
        view?.showSites(fireStore.publicSites)
    }

    fun navigateSiteView(site: SiteModel) {
        view?.findNavController()
            ?.navigate(R.id.action_siteList_dest_to_site_dest, bundleOf("site" to site))
    }

    fun setIsVisited(site: SiteModel, isVisited: Boolean) {
        fireStore.setIsVisited(site, isVisited)
    }
}