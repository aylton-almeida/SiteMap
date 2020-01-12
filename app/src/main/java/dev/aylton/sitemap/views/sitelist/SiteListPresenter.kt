package dev.aylton.sitemap.views.sitelist

import androidx.navigation.fragment.findNavController
import dev.aylton.sitemap.R
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class SiteListPresenter(view: BaseView) : BasePresenter(view) {
    fun navigateAccountView() {
        view?.findNavController()?.navigate(R.id.action_siteList_dest_to_account_dest)
    }
}