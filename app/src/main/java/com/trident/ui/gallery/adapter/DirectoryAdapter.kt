package com.trident.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trident.R
import com.trident.ui.gallery.service.RetrofitClient.BASE_URL

class DirectoryAdapter(private val directories: Map<String, List<String>>,
                       private val onClick: (String, List<String>) -> Unit):
    RecyclerView.Adapter<DirectoryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val directoryName: TextView = view.findViewById(R.id.directoryName)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_directory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DirectoryAdapter.ViewHolder, position: Int) {
        val (directory, faces) = directories.entries.toList()[position]
        holder.directoryName.text = directory
        if(faces.isNotEmpty()) {
            val firstImageUrl = BASE_URL + "load-image/$directory/${faces[0]}"
            Glide.with(holder.itemView.context).load(firstImageUrl).into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            //val faces = directories[directory] ?: emptyList()
            onClick(directory, faces)
        }
    }

    override fun getItemCount(): Int {
        return directories.size
    }
}