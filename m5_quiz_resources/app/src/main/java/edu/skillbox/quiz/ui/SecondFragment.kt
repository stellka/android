package edu.skillbox.quiz.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import edu.skillbox.quiz.R
import edu.skillbox.quiz.databinding.FragmentFirstBinding
import edu.skillbox.quiz.databinding.FragmentSecondBinding
import edu.skillbox.quiz.quiz.QuizStorage

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SecondFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val questionsList = QuizStorage.getQuiz(QuizStorage.Locale.Ru)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    var answer1 = 1
    var answer2 = 1
    var answer3 = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater)


        binding.radioQuestion1.text = questionsList.questions[0].question
        binding.radioQuestion2.text = questionsList.questions[1].question
        binding.radioQuestion3.text = questionsList.questions[2].question

        binding.radioButton1.text = questionsList.questions[0].answers[0]
        binding.radioButton2.text = questionsList.questions[0].answers[1]
        binding.radioButton3.text = questionsList.questions[0].answers[2]
        binding.radioButton4.text = questionsList.questions[0].answers[3]

        binding.radio2Button1.text = questionsList.questions[1].answers[0]
        binding.radio2Button2.text = questionsList.questions[1].answers[1]
        binding.radio2Button3.text = questionsList.questions[1].answers[2]
        binding.radio2Button4.text = questionsList.questions[1].answers[3]

        binding.radio3Button1.text = questionsList.questions[2].answers[0]
        binding.radio3Button2.text = questionsList.questions[2].answers[1]
        binding.radio3Button3.text = questionsList.questions[2].answers[2]
        binding.radio3Button4.text = questionsList.questions[2].answers[3]


        binding.radio1.setOnCheckedChangeListener { _, buttonId ->
            when (buttonId) {
                R.id.radioButton1 -> answer1 = 0
                R.id.radioButton2 -> answer1 = 1
                R.id.radioButton3 -> answer1 = 2
                R.id.radioButton4 -> answer1 = 3
            }
        }


        binding.radio2.setOnCheckedChangeListener { _, buttonId ->
            when (buttonId) {
                R.id.radio2Button1 -> answer2 = 0
                R.id.radio2Button2 -> answer2 = 1
                R.id.radio2Button3 -> answer2 = 2
                R.id.radio2Button4 -> answer2 = 3
            }
        }

        binding.radio3.setOnCheckedChangeListener { _, buttonId ->
            when (buttonId) {
                R.id.radio3Button1 -> answer3 = 0
                R.id.radio3Button2 -> answer3 = 1
                R.id.radio3Button3 -> answer3 = 2
                R.id.radio3Button4 -> answer3 = 3
            }
        }

        return binding.root
    }

    val answers = listOf(answer1, answer2, answer3)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.send.setOnClickListener {

            val bundle = Bundle().apply {
                putString("param1", QuizStorage.answer(questionsList, answers))
            }

            findNavController().navigate(R.id.action_SecondFragment_to_resultFragment, bundle)
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}