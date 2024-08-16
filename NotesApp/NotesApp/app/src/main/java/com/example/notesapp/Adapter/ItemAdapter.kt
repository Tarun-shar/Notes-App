package com.example.notesapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Models.Note
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemLayoutBinding
import kotlin.random.Random

class ItemAdapter(private var context:Context , val listener: NotesClickListener ):RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val notesList = ArrayList<Note>() // in this list we have the notes those we want to show inside recyclerView
    private val fullList = ArrayList<Note>()  // in this list we have the notes those we want access from the room database

    class ViewHolder(var binding: ItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = ItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.binding.tvTitle.text = currentNote.title
        holder.binding.tvTitle.isSelected = true

        holder.binding.tvNote.text = currentNote.note
        holder.binding.tvDate.text = currentNote.date
        holder.binding.tvDate.isSelected = true


//        set On Click Listener
        holder.binding.cardLayout.setOnClickListener {

            listener.onItemClicked(notesList[holder.adapterPosition])

        }

        holder.binding.cardLayout.setOnLongClickListener {

            listener.onLongItemClicked(notesList[holder.adapterPosition],holder.binding.cardLayout)
            true
        }
    }

    fun updateList(newList: List<Note>){

        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()

    }

//    SearchBar code
    fun filterList(search : String){
        notesList.clear()

        for(item in fullList){

            if(item.title?.lowercase()?.contains(search.lowercase()) == true ||
                    item.note?.lowercase()?.contains(search.lowercase()) == true){

                notesList.add(item)
            }
        }

    notifyDataSetChanged()

    }


    interface NotesClickListener{
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}