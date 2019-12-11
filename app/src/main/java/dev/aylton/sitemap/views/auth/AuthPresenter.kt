package dev.aylton.sitemap.views.auth

import android.graphics.Color
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dev.aylton.sitemap.R
import dev.aylton.sitemap.helpers.hideKeyboard
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthPresenter(view: BaseView) : BasePresenter(view) {

    init {
        if (hasCurrentUser())
            view.findNavController().navigate(R.id.action_signUp_dest_to_siteList_dest)
    }

    fun doSignIn(email: String, password: String) {
        hideKeyboard(view?.activity)
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                view?.findNavController()?.navigate(R.id.action_signUp_dest_to_siteList_dest)
            else
                view?.showSnackbar(it.exception?.message!!, Color.RED)
            view?.hideProgress(view?.progressBar)
        }
    }

    fun doSignUp(email: String, password: String) {
        hideKeyboard(view?.activity)
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful)
                view?.findNavController()?.navigate(R.id.action_signUp_dest_to_siteList_dest)
            else
                view?.showSnackbar(it.exception?.message!!, Color.RED)
            view?.hideProgress(view?.progressBar)
        }
    }

}