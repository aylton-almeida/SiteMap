package dev.aylton.sitemap.views.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.aylton.sitemap.R
import dev.aylton.sitemap.models.Note
import kotlinx.android.synthetic.main.card_note.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class NotesAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>(),
    AnkoLogger {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int = notes.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {

        fun bind(note: Note){
            itemView.noteText.text = note.description
        }
    }
}