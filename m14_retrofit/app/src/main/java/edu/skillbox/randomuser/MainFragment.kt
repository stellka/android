package edu.skillbox.randomuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = requireActivity().findViewById<TextView>(R.id.textView)
        val button = requireActivity().findViewById<Button>(R.id.button)
        val imageView = requireActivity().findViewById<ImageView>(R.id.imageView)
        val textView1 = requireActivity().findViewById<TextView>(R.id.textView1)

        lifecycleScope.launchWhenStarted {
            val user = RetrofitServices.searchUsersApi.getUsersInfoList()

            textView.text = "Name: " + user.results.first().name.title + " " + user.results.first().name.first  +
                    " " + user.results.first().name.last + "\n" + user.results.first().dob.age + " years old"
            textView1.text = "Email: ${user.results.first().email} \nId: ${user.results.first().id.value} \n" +
                    "Birthday: ${user.results.first().dob.date} \nPhone number: ${user.results.first().phone} \nRegistered ${user.results.first().registered.age} years ago"
            val pic = user.results.first().picture.medium
            Glide.with(this@MainFragment)
                .load(pic)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }


        button.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                val user = RetrofitServices.searchUsersApi.getUsersInfoList()

                textView.text = "Name: " + user.results.first().name.title + " " + user.results.first().name.first  +
                        " " + user.results.first().name.last + "\n" + user.results.first().dob.age + " years old"
                textView1.text = "Email: ${user.results.first().email} \nId: ${user.results.first().id.value} \n" +
                        "Birthday: ${user.results.first().dob.date} \nPhone number: ${user.results.first().phone} \nRegistered ${user.results.first().registered.age} years ago"
                val pic = user.results.first().picture.medium
                Glide.with(this@MainFragment)
                    .load(pic)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)
            }
        }
    }
}