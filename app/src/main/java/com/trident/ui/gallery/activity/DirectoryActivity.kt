package com.trident.ui.gallery.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trident.R
import com.trident.ui.gallery.adapter.DirectoryAdapter
import com.trident.ui.gallery.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectoryActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DirectoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)
        recyclerView = findViewById(R.id.directoryRecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitClient.imagesApiService.getImages()
            .enqueue(object : Callback<Map<String, List<String>>> {
                override fun onResponse(
                    call: Call<Map<String, List<String>>>,
                    response: Response<Map<String, List<String>>>
                ) {
                    if(response.isSuccessful) {
                        val directories = response.body() ?: emptyMap()
                        // adapter = DirectoryAdapter(directories)
                        adapter = DirectoryAdapter(directories) { directoryName, faces ->
                            val intent = Intent(this@DirectoryActivity, FacesActivity::class.java)
                            intent.putStringArrayListExtra("faces", ArrayList(faces))
                            intent.putExtra("directoryName", directoryName)
                            startActivity(intent)
                        }
                        recyclerView.adapter = adapter
                    } else {
                        Toast.makeText(this@DirectoryActivity, "Failed to load images",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, List<String>>>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@DirectoryActivity, "Error: ${t.message}",
                        Toast.LENGTH_SHORT).show()
                }
            })
    }
}