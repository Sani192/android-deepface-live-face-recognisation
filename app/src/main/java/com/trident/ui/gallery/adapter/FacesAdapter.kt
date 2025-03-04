package com.trident.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trident.R
import com.trident.ui.gallery.service.RetrofitClient

class FacesAdapter(private val directoryName: String,
                   private val faces: List<String>,
                   private val onFacesSelected: (List<String>) -> Unit) :
    RecyclerView.Adapter<FacesAdapter.FaceViewHolder>() {

    private val selectedFaces = mutableSetOf<String>()

    class FaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val faceView: ImageView = itemView.findViewById(R.id.faceView)
        private val selectionOverlay: View = itemView.findViewById(R.id.selectionOverlay)

        fun bind(directoryName: String, faceUrl: String, isSelected: Boolean) {
            val imageUrl = RetrofitClient.BASE_URL + "load-image/$directoryName/$faceUrl"
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(faceView)

            // show overlay
            selectionOverlay.visibility = if (isSelected) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_face, parent, false)
        return FaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaceViewHolder, position: Int) {
        val faceUrl = faces[position]
        holder.bind(directoryName, faceUrl, selectedFaces.contains(faceUrl))
        //Glide.with(holder.itemView.context).load(faceUrl).into(holder.faceView)

        // long press
        holder.itemView.setOnLongClickListener {
            toggleSelection(faceUrl)
            onFacesSelected(selectedFaces.toList())
            true
        }
    }

    override fun getItemCount(): Int = faces.size

    private fun toggleSelection(faceUrl: String) {
        if (selectedFaces.contains(faceUrl)) {
            selectedFaces.remove(faceUrl)
        } else {
            selectedFaces.add(faceUrl)
        }
        notifyDataSetChanged()
    }
}