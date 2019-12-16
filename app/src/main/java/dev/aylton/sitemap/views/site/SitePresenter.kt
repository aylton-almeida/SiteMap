package dev.aylton.sitemap.views.site

import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_site.*

class SitePresenter(view: BaseView): BasePresenter(view){

    var site: SiteModel = view.arguments!!.getParcelable("site")!!

    init {
        view.textView.text = site.name
    }
}