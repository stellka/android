package edu.skillbox.randomuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = requireActivity().findViewById<TextView>(R.id.textView)
        val button = requireActivity().findViewById<Button>(R.id.button)

        lifecycleScope.launchWhenStarted {
            val user = RetrofitServices.searchUsersApi.getUsersInfoList()
            textView.text = user.info.toString() + user.results.toString()
        }

        button.setOnClickListener{
            lifecycleScope.launchWhenStarted {
                val user = RetrofitServices.searchUsersApi.getUsersInfoList()
                textView.text = user.toString()
            }
        }
    }
}