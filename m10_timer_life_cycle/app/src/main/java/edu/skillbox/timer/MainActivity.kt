package edu.skillbox.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.slider.Slider
import edu.skillbox.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.*

private const val KEY = "i"
private const val KEY_TIMER_RUN = "start"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var isTimerRun = false
    private val myScope = CoroutineScope(Dispatchers.Default + Job())
    private var process: Job? = null
    private var i = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBarCircular.progress = 360

        binding.slider.addOnChangeListener { _, _, _ ->
            binding.textik.text = binding.slider.value.toInt().toString()
            binding.progressBarCircular.max = binding.slider.value.toInt()
        }

        binding.textik.text = "0"


        val value = savedInstanceState?.getInt(KEY)
        val isTimer = savedInstanceState?.getBoolean(KEY_TIMER_RUN, false)

        if (isTimer == true) {
            if (value != null) {
                binding.progressBarCircular.progress = value
                startTimer(value)
            }
        }

        binding.buttonStart.setOnClickListener {

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
            binding.slider.isEnabled = true
            binding.buttonStop.visibility = Button.VISIBLE
            binding.buttonStart.visibility = Button.INVISIBLE

            binding.buttonStop.setOnClickListener {
                binding.progressBarCircular.progress = 360
                isTimerRun = !isTimerRun
                binding.textik.text = binding.slider.value.toInt().toString()
                updateTheSecondUI()
                process?.cancel()
            }
        }
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

        binding.slider.isEnabled = false
        process = myScope.launch(Dispatchers.Main) {
            i = start
            while (i > 0) {
                i--
                updateView(i)
                delay(1000)
            }
            if (i == 0) isTimerRun = false
            updateView(binding.slider.value.toInt())
            updateTheSecondUI()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        process?.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY, binding.progressBarCircular.progress)
        outState.putBoolean(KEY_TIMER_RUN, isTimerRun)

    }
}