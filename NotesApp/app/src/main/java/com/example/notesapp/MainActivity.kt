package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.Adapter.ItemAdapter
import com.example.notesapp.Models.Note
import com.example.notesapp.Models.NoteViewModel
import com.example.notesapp.Room.NotesDatabase
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ItemAdapter.NotesClickListener , PopupMenu.OnMenuItemClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var database: NotesDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: ItemAdapter
    lateinit var selectedNote: Note

//    update the note
    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
         if (result.resultCode == Activity.RESULT_OK){

             val note = result.data?.getSerializableExtra("note") as? Note
             if(note != null){
                 viewModel.updateNote(note)
             }
         }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Initializing the UI
        initUI()

//        ViewModel Work
        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allnotes.observe(this) { list ->

            list?.let {
                adapter.updateList(list)
            }
        }

//        Database work
        database = NotesDatabase.getDatabase(this)

    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = ItemAdapter(this,this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

            if(result.resultCode == Activity.RESULT_OK){

                val note = result.data?.getSerializableExtra("note") as? Note
                if( note != null){
                    viewModel.insertNote(note)
                }
            }

        }

        binding.fbAddNote.setOnClickListener {
            val intent = Intent(this,AddNote::class.java)
            getContent.launch(intent)
//            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if(newText != null){
                    adapter.filterList(newText)
                }

                return true
            }

        })
    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this , AddNote::class.java)
        intent.putExtra("current_note",note)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectedNote = note
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_note){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}
