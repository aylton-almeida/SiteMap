package dev.aylton.sitemap.views.favorites

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class FavouritesPresenter(view: BaseView) : BasePresenter(view) {

    init {
        fireStore.addLoadSitesFunction({ loadSites() }, "favourites")
    }

    private fun loadSites() {
        val favouriteSites: ArrayList<SiteModel> = ArrayList()
        val allSites: ArrayList<SiteModel> = arrayListOf()
        fireStore.publicSites.forEach { allSites.add(it.copy()) }
        fireStore.privateSites.forEach { allSites.add(it.copy()) }
        for (item in fireStore.user.favourites)
            favouriteSites.add(allSites.find { it.id == item }!!)
        view?.showSites(favouriteSites)
    }

    fun navigateSiteView(site: SiteModel) {
        view?.findNavController()
            ?.navigate(R.id.action_favourites_dest_to_site_dest, bundleOf("site" to site))
    }

    fun setIsVisited(site: SiteModel, isVisited: Boolean) {
        fireStore.setIsVisited(site, isVisited)
    }
}