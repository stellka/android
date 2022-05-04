package edu.skillbox.quiz.ui

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import edu.skillbox.quiz.R
import edu.skillbox.quiz.databinding.FragmentSecondBinding
import edu.skillbox.quiz.quiz.QuizStorage

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var index:Int

        val radioGroup1 = binding.radio1
        radioGroup1.setOnCheckedChangeListener{_, buttonId ->
            when (buttonId){
                R.id.radioButton1 -> index = 0
                R.id.radioButton2 -> index = 1
                R.id.radioButton3 -> index = 2
                R.id.radioButton4 -> index = 3
            }
        }


        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        val quiz = QuizStorage
        binding.send.setOnClickListener {
            val bundle = Bundle().apply {
                putString("param1", quiz.answer(quiz = quiz.getQuiz(QuizStorage.Locale.Ru), answers = listOf(1)))
            }
            parentFragmentManager.commit {
                replace<ResultFragment>(containerViewId = R.id.fragment_container_view_tag, args = bundle)
                addToBackStack(ResultFragment::class.java.simpleName)
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.send.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_resultFragment)
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}