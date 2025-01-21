package com.example.appforphone

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch


class ListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val titlesList = resources.getStringArray(R.array.Birds).toList()
        val descriptionsList = resources.getStringArray(R.array.Descriptions).toList()
        val imagesList = listOf(R.drawable.black_rosy_finch,R.drawable.baltimore_oriole,R.drawable.bald_eagle)

        val birdsList = mutableListOf<Bird>()

        for(i in titlesList.indices){
            birdsList.add(Bird(titlesList[i],descriptionsList[i],imagesList[i]))
        }
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RVAdapter(emptyList())
        recyclerView.adapter = adapter

        val viewModel = ViewModelSearch()
        val searchBar = findViewById<SearchView>(R.id.searchBar)
        viewModel.setData(birdsList)

        lifecycleScope.launch {
            viewModel.filteredBirds.collect{ filteredBirds ->
                adapter.updateData(filteredBirds)
            }
        }

        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    viewModel.search(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 2) {
                    viewModel.search(newText)
                }
                else{
                    viewModel.search("")
                }
                return true
            }

        })
    }

}

class RVAdapter(
    private var birds:List<Bird>): RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val itemDesc: TextView = itemView.findViewById(R.id.item_desc)
        val itemImg: ImageView = itemView.findViewById(R.id.item_image)
        val itemButton: Button = itemView.findViewById(R.id.item_button)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                Toast.makeText(
                    itemView.context,
                    "You clicked on item # ${position + 1}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            itemButton.setOnClickListener {
                val position: Int = adapterPosition
                Toast.makeText(
                    itemView.context,
                    "You clicked on the Button of item # ${position + 1}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return birds.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RVAdapter", "Binding item at position: $position")

        val bird = birds[position]

        holder.itemTitle.text = bird.title
        holder.itemDesc.text = bird.desc
        holder.itemImg.setImageResource(bird.img)
    }

    public fun updateData(birdsNew: List<Bird>) {
        if (birdsNew != birds)
            birds = birdsNew
        notifyDataSetChanged()
    }
}

