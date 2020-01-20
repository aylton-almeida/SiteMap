package dev.aylton.sitemap.views.notes

import android.graphics.Color
import dev.aylton.sitemap.helpers.hideKeyboard
import dev.aylton.sitemap.models.Note
import dev.aylton.sitemap.models.SiteModel
import dev.aylton.sitemap.views.BasePresenter
import dev.aylton.sitemap.views.BaseView

class NotesPresenter(view: BaseView): BasePresenter(view){

    val site: SiteModel = view.arguments!!.getParcelable("site")!!

    init {
        view.showNotes(site.notes)
    }

    fun createNote(description: String){
        hideKeyboard(view?.activity)
        if (description.isEmpty())
            view?.showSnackbar("Insert a note", Color.RED)
        else {
            val note = Note(fireStore.user.id, fireStore.user.email, description)
            site.notes.add(note)
            fireStore.update(site)
        }
    }
}