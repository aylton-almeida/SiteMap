package dev.aylton.sitemap.views

import dev.aylton.sitemap.MainApp

open class BasePresenter(var view: BaseView?) {

    var app: MainApp = view?.activity?.application as MainApp

    open fun onDestroy() {
        view = null
    }
}