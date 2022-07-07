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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBarCircular.progress = 360

        binding.slider.addOnChangeListener { _, _, _ ->
            binding.textik.text = binding.slider.value.toInt().toString()
            binding.progressBarCircular.max = binding.slider.value.toInt()
            binding.progressBarCircular.progress = 360
        }

        binding.textik.text = "0"

        val value = savedInstanceState?.getInt(KEY)
        val isTimer = savedInstanceState?.getBoolean(KEY_TIMER_RUN, false)

        if (isTimer == true) {
            if (value != null) {
                startTimer(value)
                binding.buttonStop.setOnClickListener {
                    stopProcess()
                }
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
                binding.buttonStart.visibility = Button.VISIBLE
                binding.buttonStop.visibility = Button.INVISIBLE
            }

            isTimerRun = !isTimerRun
            binding.slider.isEnabled = !isTimerRun

            binding.buttonStop.setOnClickListener {
                stopProcess()
            }
        }
    }

    private suspend fun updateView(millis: Int) {
        withContext(Dispatchers.Main) {
            binding.textik.text = millis.toString()
            binding.progressBarCircular.progress = millis
            binding.slider.isEnabled = false
        }
    }

    private fun startTimer(start: Int) {
        process = myScope.launch(Dispatchers.Main) {
            var i = start
            while (i > 0) {
                binding.buttonStart.visibility = Button.INVISIBLE
                binding.buttonStop.visibility = Button.VISIBLE
                i--
                updateView(i)
                delay(1000)
                isTimerRun = true
            }
            if (i == 0)
                Toast.makeText(this@MainActivity, "Время вышло!", Toast.LENGTH_SHORT).show()
            isTimerRun = false
            updateView(binding.slider.value.toInt())
            binding.buttonStart.visibility = Button.VISIBLE
            binding.buttonStop.visibility = Button.INVISIBLE
            binding.slider.isEnabled = true
        }
    }

    private fun stopProcess() {
        binding.progressBarCircular.progress = 360
        isTimerRun = false
        binding.textik.text = binding.slider.value.toInt().toString()
        binding.buttonStart.visibility = Button.VISIBLE
        binding.buttonStop.visibility = Button.INVISIBLE
        binding.slider.isEnabled = true
        process?.cancel()
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