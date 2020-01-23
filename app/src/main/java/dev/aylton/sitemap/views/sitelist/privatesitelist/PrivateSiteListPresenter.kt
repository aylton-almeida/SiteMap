package dev.aylton.sitemap.views.sitelist.privatesitelist

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class PrivateSiteListPresenter(view: BaseView) : BasePresenter(view) {

    init {
        fireStore.addLoadSitesFunction({ loadSites() }, "private")
    }

    private fun loadSites() {
        view?.showSites(fireStore.privateSites)
    }

    fun navigateSiteView(site: SiteModel) {
        view?.findNavController()
            ?.navigate(R.id.action_siteList_dest_to_site_dest, bundleOf("site" to site))
    }

    fun setIsVisited(site: SiteModel, isVisited: Boolean) {
        fireStore.setIsVisited(site, isVisited)
    }

    fun navigateEditSiteView() {
        view?.findNavController()
            ?.navigate(R.id.action_siteList_dest_to_editSite_dest, bundleOf("isEditMode" to false))
    }
}