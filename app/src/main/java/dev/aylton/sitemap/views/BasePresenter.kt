package dev.aylton.sitemap.views

import android.content.Intent
import dev.aylton.sitemap.MainApp
import dev.aylton.sitemap.services.firebase.SiteFireStore
import org.jetbrains.anko.AnkoLogger

open class BasePresenter(var view: BaseView?): AnkoLogger {

    var app: MainApp = view?.activity?.application as MainApp
    var fireStore: SiteFireStore

    init {
        fireStore = app.site as SiteFireStore
    }

    fun hasCurrentUser(): Boolean {
        return fireStore.hasCurrentUser()
    }

    open fun onDestroy() {
        view = null
    }

    open fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    }
}