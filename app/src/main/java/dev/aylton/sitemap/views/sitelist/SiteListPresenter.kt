package dev.aylton.sitemap.views.sitelist

import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class SiteListPresenter(view: BaseView) : BasePresenter(view) {
    init {
        fireStore.fetchSites()
    }
}