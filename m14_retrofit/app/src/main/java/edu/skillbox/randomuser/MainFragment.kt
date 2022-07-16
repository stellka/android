package edu.skillbox.randomuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide

private const val BASE_URL = "https://randomuser.me/api/portraits/med/women/26.jpg"

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = requireActivity().findViewById<TextView>(R.id.textView)
        val button = requireActivity().findViewById<Button>(R.id.button)
        val imageView = requireActivity().findViewById<ImageView>(R.id.imageView)

        lifecycleScope.launchWhenStarted {
            val user = RetrofitServices.searchUsersApi
            val info = user.getUsersInfoList()
            val pic = user.getPic().medium
            textView.text = info.toString()
            Glide.with(this@MainFragment)
                .load(pic)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }

        button.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                val user = RetrofitServices.searchUsersApi.getUsersInfoList()
                textView.text = user.toString()
                val pic = RetrofitServices.searchUsersApi.getPic().medium
                Glide.with(this@MainFragment)
                    .load(pic)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)
            }
        }
    }
}