package com.trident.ui.gallery.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trident.R
import com.trident.ui.gallery.adapter.FacesAdapter
import com.trident.ui.gallery.models.MoveImagesRequest
import com.trident.ui.gallery.service.RetrofitClient

class FacesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FacesAdapter
    private lateinit var noFacesTextView: TextView
    private var selectedImages: List<String> = emptyList()
    private var directoryName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faces)

        val faces = intent.getStringArrayListExtra("faces") ?: emptyList<String>()
        directoryName = intent.getStringExtra("directoryName") ?: ""

        // title
        supportActionBar?.title = directoryName

        // initialize views
        recyclerView = findViewById(R.id.facesRecyclerview)
        noFacesTextView = findViewById(R.id.noFaces)

        if(faces.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            noFacesTextView.visibility = View.GONE

            adapter = FacesAdapter(directoryName!!, faces) { selected ->
                selectedImages = selected
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(this, 3)
        } else {
            recyclerView.visibility = View.GONE
            noFacesTextView.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.knock_knock_faces_menus, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_move_images -> {
                moveSelectedImages()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveSelectedImages() {
        if (selectedImages.isEmpty()) {
            Toast.makeText(this@FacesActivity, "No images selected", Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(this@FacesActivity, "Moving ${selectedImages.size} images", Toast.LENGTH_SHORT).show()

        val response = RetrofitClient.imagesApiService.moveImages(directoryName, selectedImages).execute()
        if (response.isSuccessful) {
            Toast.makeText(this@FacesActivity, "Images moved successfully", Toast.LENGTH_SHORT).show()
            selectedImages = emptyList()
        } else {
            Toast.makeText(this@FacesActivity, "Failed to move images", Toast.LENGTH_SHORT).show()
        }
    }
}