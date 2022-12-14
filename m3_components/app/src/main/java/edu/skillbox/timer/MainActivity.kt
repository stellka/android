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
import kotlinx.coroutines.*

private const val KEY = "key"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var currentProgress: Int = 0
        val circleProgress = findViewById<ProgressBar>(R.id.progressBarCircular)
        val button = findViewById<Button>(R.id.button_start)
        val button2 = findViewById<Button>(R.id.button_stop)
        val textik = findViewById<TextView>(R.id.textik)
        val slid = findViewById<Slider>(R.id.slider)
        var isTimerRun = true

        savedInstanceState?.let { bundle ->
            bundle.getString(KEY, "default value")
        }
        val handler = Handler(Looper.getMainLooper())

        textik.text = "0"
        var i: Int

        slid.addOnChangeListener { _, _, _ ->
            textik.text = slid.value.toInt().toString()
        }

        val updateProgressBar = {
            circleProgress.progress = currentProgress
        }

        fun updateUI() {
            slid.isEnabled = false
            button2.visibility = Button.VISIBLE
            button.visibility = Button.INVISIBLE
            updateProgressBar()
        }

        fun updateTheSecondUI() {
            button.visibility = Button.VISIBLE
            button2.visibility = Button.INVISIBLE
            slid.isEnabled = true
            Toast.makeText(this, "Время вышло!", Toast.LENGTH_SHORT).show()
        }

        button.setOnClickListener {

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
                        updateProgressBar()
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


            handler.post{
                button2.setOnClickListener {
                    updateTheSecondUI()
                    textik.text = slid.value.toInt().toString()
                    circleProgress.progress = 0
                    scope.cancel()
                }
                updateUI()
            }
        }
        }


        override fun onSaveInstanceState(outState: Bundle) {
            outState.putString(KEY, "string")
            super.onSaveInstanceState(outState)
        }
    }