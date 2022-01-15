package edu.skillbox.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.slider.Slider
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var currentProgress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val circleProgress = findViewById<ProgressBar>(R.id.progressBarCircular)
        val button = findViewById<Button>(R.id.button_start)
        val textik = findViewById<TextView>(R.id.textik)
        val slid = findViewById<Slider>(R.id.slider)

        val updateProgress = {
            circleProgress.progress = currentProgress

        }

        button.setOnClickListener {
            button.text = "STOP"

            val parentJob = Job()
            val scope = CoroutineScope(parentJob + Dispatchers.Default)
            suspend fun main() {
                var i = slid.value.toInt()
                scope.launch {
                    while (isActive) {
                        yield()
                        while (i > 0) {
                            if (currentProgress > 0) {
                                currentProgress -= 1
                                updateProgress()
                            }
                            slid.isActivated = false
                            i--
                            textik.text = i.toString()
                        }
                        if (i == 0) {
                            button.text = "START"
                            cancel()
                        }
                        delay(500)
                    }
                    parentJob.complete()
                }

                slid.addOnChangeListener { _, _, _ ->
                    // Responds to when slider's value is changed
                    textik.text = slid.value.toInt().toString()
                }
            }
        }
    }
}