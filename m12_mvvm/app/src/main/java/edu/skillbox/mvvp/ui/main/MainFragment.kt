package edu.skillbox.mvvp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import edu.skillbox.mvvp.R
import edu.skillbox.mvvp.State
import edu.skillbox.mvvp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val find = binding.login.text.toString()
            viewModel.onSignInClick(find)
        }

        binding.login.addTextChangedListener {charSequence->
            val searchString = charSequence.toString()
            viewModel.onEditText(searchString)
        }


        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.state
                    .collect{ state ->
                        when (state){
                            State.Loading -> {
                                binding.progress.isVisible = true
                                binding.loginLayout.error = null
                                binding.button.isEnabled = false
                            }
                            State.Success -> {
                                binding.progress.isVisible = false
                                binding.loginLayout.error = null
                                binding.button.isEnabled = true
                                binding.textView.text = "По запросу "+binding.login.text+" ничего не найдено"
                            }
                            State.Error -> {
                                binding.button.isEnabled = false
                                binding.progress.isVisible = false
                                binding.loginLayout.error = null
                            }
                            State.Ready -> {
                                binding.button.isEnabled = true
                                binding.progress.isVisible = false
                            }
                        }
                    }
            }
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.error
                    .collect {message ->
                        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                    }
            }
    }
}