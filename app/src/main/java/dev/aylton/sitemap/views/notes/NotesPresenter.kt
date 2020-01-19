package dev.aylton.sitemap.views.notes

import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class NotesPresenter(view: BaseView): BasePresenter(view){

    val site: SiteModel = view.arguments!!.getParcelable("site")!!

    init {
        view.showNotes(site.notes)
    }

    fun createNote(){
        // TODO: Implement
    }
}