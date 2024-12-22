package com.example.appforphone

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListActivity : AppCompatActivity() {

    lateinit var titlesList: Array<String>
    lateinit var descriptionsList: Array<String>
    lateinit var imagesList: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titlesList = resources.getStringArray(R.array.Birds)
        descriptionsList = resources.getStringArray(R.array.Descriptions)
        imagesList = arrayOf(R.drawable.black_rosy_finch,R.drawable.baltimore_oriole,R.drawable.bald_eagle)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RVAdapter(titlesList, descriptionsList,imagesList)

    }

}

class RVAdapter(
    private var titles:Array<String>,
    private var descriptions:Array<String>,
    private var images:Array<Int>): RecyclerView.Adapter<RVAdapter.ViewHolder>() {

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
            itemButton.setOnClickListener{
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
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RVAdapter", "Binding item at position: $position")
        holder.itemTitle.text = titles[position]
        holder.itemDesc.text = descriptions[position]
        holder.itemImg.setImageResource(images[position])
        }
    }

