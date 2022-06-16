package edu.skillbox.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.slider.Slider
import edu.skillbox.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.*

private const val KEY_SLIDER = "slider"
private const val KEY_TIMER_RUN = "start"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var isTimerRun = false
    private var startPoint = 0
    private val myScope = CoroutineScope(Dispatchers.Default + Job())
    private var process: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sliderValue = savedInstanceState?.getInt(KEY_SLIDER)

        if (sliderValue != null) startPoint = sliderValue.toInt()

        binding.slider.addOnChangeListener { _, _, _ ->

            binding.textik.text = binding.slider.value.toInt().toString()
        }


        binding.textik.text = startPoint.toString()
        binding.progressBarCircular.progress = 360

        binding.buttonStart.setOnClickListener {
            binding.progressBarCircular.max = binding.slider.value.toInt()
            binding.textik.text = startPoint.toString()

            if (!isTimerRun) {
                if (binding.progressBarCircular.progress > 0) {
                    binding.buttonStop.visibility = Button.VISIBLE
                    binding.buttonStart.visibility = Button.INVISIBLE
                    startTimer(binding.slider.value.toInt())
                }
            } else {
               updateTheSecondUI()
            }
            isTimerRun = !isTimerRun
            binding.slider.isEnabled = !isTimerRun
            binding.progressBarCircular.progress = 360
            updateUI()

            binding.buttonStop.setOnClickListener {
                isTimerRun = !isTimerRun
                updateTheSecondUI()
                binding.textik.text = binding.slider.value.toInt().toString()
                binding.progressBarCircular.progress = 360
                process?.cancel()
            }
        }
    }

    private fun updateUI() {
        binding.slider.isEnabled = false
        binding.buttonStop.visibility = Button.VISIBLE
        binding.buttonStart.visibility = Button.INVISIBLE
    }

    private fun updateTheSecondUI() {
        binding.buttonStart.visibility = Button.VISIBLE
        binding.buttonStop.visibility = Button.INVISIBLE
        binding.slider.isEnabled = true
        Toast.makeText(this, "Время вышло!", Toast.LENGTH_SHORT).show()
    }

    private suspend fun updateView(millis: Int) {
        withContext(Dispatchers.Main) {
            binding.textik.text = millis.toString()
            binding.progressBarCircular.progress = millis
            binding.slider.isEnabled = false
        }
    }

    private fun startTimer(start: Int) {
        updateUI()
        process = myScope.launch(Dispatchers.Main) {
            var i = start
            while (i > 0) {
                i--
                updateView(i)
                delay(1000)

            }
            isTimerRun = false
            updateTheSecondUI()
            updateView(binding.slider.value.toInt())
            binding.progressBarCircular.progress = 360
            process?.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        process?.cancel()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.getInt(KEY_SLIDER, binding.progressBarCircular.progress)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getInt(KEY_SLIDER)
        binding.progressBarCircular.max = binding.slider.value.toInt()
        binding.progressBarCircular.progress = startPoint
        if (!isTimerRun) {
            if (binding.progressBarCircular.progress > 0) {
                binding.buttonStop.visibility = Button.VISIBLE
                binding.buttonStart.visibility = Button.INVISIBLE
                startTimer(startPoint)
            }
        } else {
            updateTheSecondUI()
        }
        isTimerRun = !isTimerRun
        binding.slider.isEnabled = isTimerRun
        binding.progressBarCircular.progress = 360

        binding.buttonStop.setOnClickListener {
            isTimerRun = !isTimerRun
            updateTheSecondUI()
            binding.textik.text = binding.slider.value.toInt().toString()
            binding.progressBarCircular.progress = 360
            process?.cancel()
        }

    }
}