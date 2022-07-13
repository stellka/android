package edu.skillbox.randomuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.concurrent.thread


class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = requireActivity().findViewById<TextView>(R.id.textView)

        lifecycleScope.launchWhenStarted {
            val user = RetrofitServices.searchUsersApi.getUsersInfoList()
            textView.text = user.first().info.toString()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

}