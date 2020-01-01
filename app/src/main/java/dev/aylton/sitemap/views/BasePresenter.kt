package dev.aylton.sitemap.views

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import dev.aylton.sitemap.MainApp
import dev.aylton.sitemap.models.firebase.SiteFireStore
import org.jetbrains.anko.AnkoLogger

open class BasePresenter(var view: BaseView?): AnkoLogger {

    var app: MainApp = view?.activity?.application as MainApp
    val auth = FirebaseAuth.getInstance()
    var fireStore: SiteFireStore

    init {
        fireStore = app.site as SiteFireStore
    }

    fun hasCurrentUser(): Boolean {
        return auth.currentUser != null
    }

    open fun onDestroy() {
        view = null
    }

    open fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    }
}