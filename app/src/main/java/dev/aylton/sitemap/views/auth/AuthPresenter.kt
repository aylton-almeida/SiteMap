package dev.aylton.sitemap.views.auth

import android.graphics.Color
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dev.aylton.sitemap.R
import dev.aylton.sitemap.helpers.hideKeyboard
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_auth.*
import org.jetbrains.anko.info

class AuthPresenter(view: BaseView?) : BasePresenter(view) {

    init {
        info { }
        if (hasCurrentUser()) {
            view?.findNavController()?.navigate(R.id.action_signUp_dest_to_siteList_dest)
        }
    }

    fun doSignIn(email: String, password: String) {
        hideKeyboard(view?.activity)
        fireStore.loginUser(email, password, {
            view?.findNavController()?.navigate(R.id.action_signUp_dest_to_siteList_dest)
            view?.toggleEnable(true)
            view?.hideProgress(view?.progressBar)
        }, {
            view?.showSnackbar(it, Color.RED)
            view?.toggleEnable(true)
            view?.hideProgress(view?.progressBar)
        })
    }

    fun doSignUp(email: String, password: String) {
        hideKeyboard(view?.activity)
        fireStore.createUser(email, password, {
            view?.findNavController()?.navigate(R.id.action_signUp_dest_to_siteList_dest)
            view?.toggleEnable(true)
            view?.hideProgress(view?.progressBar)
        }, {
            view?.showSnackbar(it, Color.RED)
            view?.toggleEnable(true)
            view?.hideProgress(view?.progressBar)
        })
    }
}