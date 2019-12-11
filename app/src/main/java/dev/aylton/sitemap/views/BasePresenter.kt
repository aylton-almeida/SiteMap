package dev.aylton.sitemap.views

import com.google.firebase.auth.FirebaseAuth
import dev.aylton.sitemap.MainApp

open class BasePresenter(var view: BaseView?) {

    val auth = FirebaseAuth.getInstance()

    var app: MainApp = view?.activity?.application as MainApp

    fun hasCurrentUser(): Boolean {
        return auth.currentUser != null
    }

    open fun onDestroy() {
        view = null
    }
}