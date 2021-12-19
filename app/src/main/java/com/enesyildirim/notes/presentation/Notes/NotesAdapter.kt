package com.enesyildirim.notes.presentation.Notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enesyildirim.notes.databinding.NotesRowDesignBinding
import com.enesyildirim.notes.domain.model.Note

class NotesAdapter(
    val shortClickListener: (Note) -> Unit,
    val longClickListener: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private var notes: List<Note> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NotesRowDesignBinding.inflate(inflater)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(notes[position])

    override fun getItemCount(): Int = notes.size

    inner class ViewHolder(private val binding: NotesRowDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                this.note = note
                executePendingBindings()
                root.setOnClickListener { shortClickListener(note) }
                root.setOnLongClickListener {
                    longClickListener(note)
                    true
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }
}