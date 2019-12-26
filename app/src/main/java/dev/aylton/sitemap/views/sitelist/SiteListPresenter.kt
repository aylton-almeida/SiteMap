package dev.aylton.sitemap.views.sitelist

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class SiteListPresenter(view: BaseView) : BasePresenter(view) {

    init {
        fireStore.fetchSites { docs: ArrayList<SiteModel> -> loadSites(docs) }
    }

    private fun loadSites(sites: ArrayList<SiteModel>) {
        view?.showSites(sites)
    }

    fun navigateSiteView(site: SiteModel){
        view?.findNavController()?.navigate(R.id.action_siteListView_to_siteView, bundleOf("site" to site))
    }

    fun navigateAccountView(){
        view?.findNavController()?.navigate(R.id.action_siteList_dest_to_account_dest)
    }

    fun setIsVisited(site: SiteModel, isVisited: Boolean){
        fireStore.setIsVisited(site, isVisited)
    }
}