package edu.skillbox.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.slider.Slider
import edu.skillbox.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.*

private const val KEY_SLIDER = "slider"
private const val KEY_TIMER_RUN = "start"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var i = 0
    private var isTimerRun = false
    private var startPoint = 0
    private var enabledSlider = true
    private val myScope = CoroutineScope(Dispatchers.Default + Job())
    private var process: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val circleProgress = findViewById<ProgressBar>(R.id.progressBarCircular)
        val button = findViewById<Button>(R.id.button_start)
        val button2 = findViewById<Button>(R.id.button_stop)
        val textik = findViewById<TextView>(R.id.textik)
        val slid = findViewById<Slider>(R.id.slider)

        val sliderValue = savedInstanceState?.getInt(KEY_SLIDER)
        val isSliderEnable = savedInstanceState?.getBoolean(KEY_TIMER_RUN, true)


        if (isSliderEnable != null) {
            slid.isEnabled = isSliderEnable
        }

        slid.addOnChangeListener { _, _, _ ->

            if (!isTimerRun){
                startPoint = slid.value.toInt()
            } else {
                if (sliderValue != null) startPoint = sliderValue.toInt()
            }
            slid.isEnabled = enabledSlider
            textik.text = startPoint.toString()
        }


        fun updateUI() {
            slid.isEnabled = false
            button2.visibility = Button.VISIBLE
            button.visibility = Button.INVISIBLE
        }

        fun updateTheSecondUI() {
            button.visibility = Button.VISIBLE
            button2.visibility = Button.INVISIBLE
            slid.isEnabled = true
            Toast.makeText(this, "Время вышло!", Toast.LENGTH_SHORT).show()
        }

        textik.text = startPoint.toString()
        circleProgress.progress = 360

        suspend fun updateView(millis: Int) {
            withContext(Dispatchers.Main) {
                textik.text = millis.toString()
                circleProgress.progress = millis
                slid.isEnabled = !isTimerRun
            }
        }


        fun startTimer(start: Int) {
            updateUI()
            process = myScope.launch(Dispatchers.Main) {
                i = start
                while (i > 0) {
                    i--
                    updateView(i)
                    delay(1000)
                }
                isTimerRun = !isTimerRun
                updateTheSecondUI()
                updateView(startPoint)
                circleProgress.progress = 360
            }
        }


        button.setOnClickListener {
            circleProgress.max = slid.value.toInt()
            textik.text = startPoint.toString()

            if (!isTimerRun) {
                if (circleProgress.progress > 0) {
                    button2.visibility = Button.VISIBLE
                    button.visibility = Button.INVISIBLE
                    startTimer(startPoint)
                }
            } else {
                button2.visibility = Button.INVISIBLE
                button.visibility = Button.VISIBLE

            }
            isTimerRun = !isTimerRun
            slid.isEnabled = !isTimerRun
            circleProgress.progress = 360

            button2.setOnClickListener {
                isTimerRun = !isTimerRun
                updateTheSecondUI()
                textik.text = slid.value.toInt().toString()
                circleProgress.progress = 360
                process?.cancel()
            }
            updateUI()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.getInt(KEY_SLIDER, i)
        outState.getBoolean(KEY_TIMER_RUN, enabledSlider)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.getInt(KEY_SLIDER)
        savedInstanceState.getInt(KEY_TIMER_RUN)
        super.onRestoreInstanceState(savedInstanceState)
    }
}