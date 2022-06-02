package edu.skillbox.quiz.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import edu.skillbox.quiz.R
import edu.skillbox.quiz.databinding.FragmentFirstBinding
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.ManagerFactoryParameters

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd.MM.yy")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.start.animate().apply {
//            duration = 3000
//            rotation(360f)
//            translationY(-10f)
//            interpolator = AccelerateDecelerateInterpolator()
//        }.start()

        binding.start.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.openTimePickerForBirthday.setOnClickListener {
            val dateDialog = MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.choose_the_date_dialog_title))
                .build()
            dateDialog.addOnPositiveButtonClickListener { timeInMillis ->
                calendar.timeInMillis = timeInMillis
//                val day = calendar.get(Calendar.DAY_OF_MONTH)
//                val month = calendar.get(Calendar.MONTH) + 1
//                val year = calendar.get(Calendar.YEAR)
//                val text = "$day.$month.$year"

                Snackbar.make(binding.openTimePickerForBirthday, dateFormat.format(calendar.time), Snackbar.LENGTH_SHORT).show()
            }
            dateDialog.show(childFragmentManager, "DatePicker")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}