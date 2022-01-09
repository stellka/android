package edu.skillbox.myfirstapplication

import android.graphics.Color.argb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.TextView
import edu.skillbox.myfirstapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var counter = 0

    private fun process(binding: ActivityMainBinding) {
        binding.plusOTwoButton.isEnabled = true
        binding.skillboxTextViewTwo.visibility = View.VISIBLE
    }

    private fun renew(){
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.plusOTwoButton.isEnabled = false
        binding.plusOneButton.setOnClickListener {
            counter++
            binding.botton.text = counter.toString()
            if (counter == 0) {
                binding.skillboxTextView.text =
                    binding.skillboxTextView.context.getText(R.string.textNameTwo)
                binding.plusOTwoButton.isEnabled = false
            }
            if (counter >= 1) {
                binding.skillboxTextView.visibility = View.INVISIBLE
                binding.count.text = (50 - counter).toString()
                process(binding)
            }
            if (counter in 40..49) {
                binding.skillboxTextView.visibility = View.INVISIBLE
                binding.count.text = (49 - counter).toString()
                process(binding)
            }
            if (counter == 50) {
                binding.skillboxTextViewThree.visibility = View.VISIBLE
                binding.plusOneButton.isEnabled = true
                process(binding)
                binding.skillboxTextViewTwo.visibility = View.INVISIBLE
                binding.plusThreeButton.visibility = View.VISIBLE
                binding.count.visibility = View.INVISIBLE
            }
        }

        binding.plusOTwoButton.setOnClickListener {
            if (counter == 0)
                binding.plusOTwoButton.isEnabled = false
            else if (counter in 1..50) {
                binding.plusOTwoButton.isEnabled = true
                counter--
            }
            if (counter in 41..49) {
                binding.skillboxTextViewThree.visibility = View.INVISIBLE
                binding.skillboxTextViewTwo.visibility = View.VISIBLE
                counter--
            }
            binding.count.text = (50 - counter).toString()
            binding.botton.text = counter.toString()
        }

        binding.plusThreeButton.setOnClickListener {
            counter = 0
            binding.botton.text = counter.toString()
            binding.plusOneButton.isEnabled = true
            binding.plusOTwoButton.isEnabled = false
            binding.plusThreeButton.visibility = View.INVISIBLE
            binding.skillboxTextViewThree.visibility = View.INVISIBLE
            binding.skillboxTextView.visibility = View.VISIBLE
            binding.skillboxTextView.text =
                binding.skillboxTextView.context.getText(R.string.textNameTwo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        renew()
    }
}