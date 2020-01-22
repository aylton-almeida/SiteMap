package dev.aylton.sitemap.views.account

import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class AccountPresenter(view: BaseView) : BasePresenter(view) {

    init {
        view.showUserData(fireStore.user, fireStore.publicSites.size, fireStore.privateSites.size)
    }
}
