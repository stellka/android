package edu.skillbox.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.slider.Slider
import edu.skillbox.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.*

private const val KEY = "key"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var currentProgress = 0
    var i = 0

    var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textik.text = "0"

        savedInstanceState?.getInt(KEY, count)

        binding.slider.addOnChangeListener { _, _, _ ->
            binding.textik.text = binding.slider.value.toInt().toString()
        }

        startTimer(i)
    }
    val updateProgressBar = {
        binding.progressBarCircular.progress = currentProgress
    }
    fun updateUI() {
        binding.slider.isEnabled = false
        binding.buttonStop.visibility = Button.VISIBLE
        binding.buttonStart.visibility = Button.INVISIBLE
        updateProgressBar()
    }

    fun updateTheSecondUI() {
        binding.buttonStart.visibility = Button.VISIBLE
        binding.buttonStop.visibility = Button.INVISIBLE
        binding.slider.isEnabled = true
        Toast.makeText(this, "Время вышло!", Toast.LENGTH_SHORT).show()
    }

    fun startTimer(int: Int){
        i = int

        binding.buttonStart.setOnClickListener {

            if (binding.progressBarCircular.progress > 0) {
                Toast.makeText(this, "Время пошло!", Toast.LENGTH_SHORT).show()
            }

            val scope = CoroutineScope(Dispatchers.Main)

            scope.launch {

                currentProgress = 0
                i = binding.slider.value.toInt()
                updateUI()
                binding.progressBarCircular.max = i


                while (i > 0) {
                    i--
                    currentProgress++
                    binding.textik.text = i.toString()
                    updateProgressBar()
                    delay(1000)

                    if (i == 0) {
                        cancel()
                        updateTheSecondUI()
                        binding.textik.text = binding.slider.value.toInt().toString()
                        binding.progressBarCircular.progress = 0
                        binding.progressBarCircular.progressDrawable
                    }
                }
            }

            binding.buttonStop.setOnClickListener {
                updateTheSecondUI()
                binding.textik.text = binding.slider.value.toInt().toString()
                binding.progressBarCircular.progress = 0
                scope.cancel()
            }
            updateUI()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KEY", i)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        count = savedInstanceState.getInt(KEY)
        startTimer(count)
    }
}