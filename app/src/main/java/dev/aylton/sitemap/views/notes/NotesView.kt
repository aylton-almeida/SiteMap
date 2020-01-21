package dev.aylton.sitemap.views.notes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.Note
import dev.aylton.sitemap.views.BaseView
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesView : BaseView() {

    lateinit var presenter: NotesPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = initPresenter(NotesPresenter(this)) as NotesPresenter

        init(upEnabled = true, optionsMenu = false)

        fab.setOnClickListener {
            presenter.createNote(inputNote.text.toString())
            inputNote.setText("")
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun showNotes(notes: ArrayList<Note>) {
        recyclerView.adapter = NotesAdapter(notes)
    }
}
