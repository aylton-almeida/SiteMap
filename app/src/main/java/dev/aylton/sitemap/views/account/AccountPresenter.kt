package dev.aylton.sitemap.views.account

import androidx.navigation.fragment.findNavController
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.UserModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import org.jetbrains.anko.info

class AccountPresenter(view: BaseView) : BasePresenter(view) {

    init {
        view.showUserData(fireStore.user, fireStore.publicSites.size, fireStore.privateSites.size)
    }

    fun doSignOut(){
        auth.signOut()
        view?.findNavController()?.navigate(R.id.action_account_dest_to_auth_dest)
    }


}
