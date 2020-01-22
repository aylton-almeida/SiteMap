package dev.aylton.sitemap.views.account

import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import org.jetbrains.anko.info

class AccountPresenter(view: BaseView) : BasePresenter(view) {

    init {
        view.toggleEnable(false)
        fireStore.getAllSites { showUserData(it) }
    }

    private fun showUserData(sitesList: ArrayList<SiteModel>) {
        var publicCount = 0
        var privateCount = 0
        for (site in sitesList)
            if (site.public)
                publicCount++
            else
                privateCount++
        view?.toggleEnable(true)
        view?.showUserData(fireStore.user, publicCount, privateCount)
    }
}
