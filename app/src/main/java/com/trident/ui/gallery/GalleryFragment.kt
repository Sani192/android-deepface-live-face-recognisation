package com.trident.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trident.R
import com.trident.databinding.FragmentGalleryBinding
import com.trident.ui.gallery.activity.DirectoryActivity
import com.trident.utils.NetworkUtils


class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val toggle: ToggleButton = binding.btnKnockKnock
        toggle.setOnCheckedChangeListener { _, isChecked ->
            NetworkUtils.callGetEndPoint(isChecked) {
                    success, response ->
                activity?.runOnUiThread {
                    if(success) {
                        Toast.makeText(activity?.applicationContext, "Response: $response", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(activity?.applicationContext, "Error: $response", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.knock_knock_sub_menus, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.knock_knock_faces -> {
                // Toast.makeText(activity?.applicationContext, "Faces", Toast.LENGTH_LONG).show()
                val intent = Intent(requireContext(), DirectoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendRequest(isChecked: Boolean) {
        NetworkUtils.callGetEndPoint(isChecked) {
                success, response ->
            if(success) {
                Toast.makeText(activity?.applicationContext, "Response: $response", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity?.applicationContext, "Error: $response", Toast.LENGTH_LONG).show()
            }
        }
    }

}