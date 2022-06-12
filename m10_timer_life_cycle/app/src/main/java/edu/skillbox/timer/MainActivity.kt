package edu.skillbox.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.slider.Slider
import edu.skillbox.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.math.IEEErem

private const val KEY = "key"
private const val KEY_INT = "int"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var i: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        var currentProgress = 0
        val circleProgress = findViewById<ProgressBar>(R.id.progressBarCircular)
        val button = findViewById<Button>(R.id.button_start)
        val button2 = findViewById<Button>(R.id.button_stop)
        val textik = findViewById<TextView>(R.id.textik)
        val slid = findViewById<Slider>(R.id.slider)
//        var isTimerRun = true


        var count = 0
        count = savedInstanceState?.getInt(KEY_INT, i)!!
        binding.textik.text = count.toString()

        val handler = Handler(Looper.getMainLooper())
        textik.text = "0"

        slid.addOnChangeListener { _, _, _ ->
            textik.text = slid.value.toInt().toString()
        }

        fun updateUI() {
            slid.isEnabled = false
            button2.visibility = Button.VISIBLE
            button.visibility = Button.INVISIBLE
            circleProgress.progress = currentProgress
        }

        fun updateTheSecondUI() {
            button.visibility = Button.VISIBLE
            button2.visibility = Button.INVISIBLE
            slid.isEnabled = true
            Toast.makeText(this, "Время вышло!", Toast.LENGTH_SHORT).show()
        }

        button.setOnClickListener {

            i = count

            if (circleProgress.progress > 0) {
                Toast.makeText(this, "Время пошло!", Toast.LENGTH_SHORT).show()
            }

            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {

                currentProgress = 0
                i = slid.value.toInt()
                updateUI()
                circleProgress.max = i

                while (i > 0) {
                    i--
                    currentProgress++
                    textik.text = i.toString()
                    circleProgress.progress = currentProgress
                    delay(1000)

                    if (i == 0) {
                        cancel()
                        updateTheSecondUI()
                        textik.text = slid.value.toInt().toString()
                        circleProgress.progress = 0
                        circleProgress.progressDrawable
                    }
                }
            }

                button2.setOnClickListener {
                    updateTheSecondUI()
                    textik.text = slid.value.toInt().toString()
                    circleProgress.progress = 0
                    scope.cancel()
                }
                updateUI()
        }
    }


    suspend fun updateView(millis: Int){
        withContext(Dispatchers.Main){
            binding.textik.text = millis.toString()
            binding.progressBarCircular.progress = millis
            binding.slider.isEnabled = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_INT, i)
        binding.textik.text = KEY_INT
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.getInt(KEY_INT)
        super.onRestoreInstanceState(savedInstanceState)
    }
}
