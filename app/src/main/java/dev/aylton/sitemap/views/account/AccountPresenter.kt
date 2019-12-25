package dev.aylton.sitemap.views.account

import androidx.navigation.fragment.findNavController
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class AccountPresenter(view: BaseView) : BasePresenter(view) {

    private fun loadSite(site: SiteModel){
        TODO("Implement")
    }

    fun doSignOut(){
        auth.signOut()
        view?.findNavController()?.navigate(R.id.action_account_dest_to_auth_dest)
    }

}