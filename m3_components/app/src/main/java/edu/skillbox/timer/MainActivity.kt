package edu.skillbox.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.slider.Slider
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive

class MainActivity : AppCompatActivity() {

    private var currentProgress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val circleProgress = findViewById<ProgressBar>(R.id.progressBarCircular)
        val button = findViewById<Button>(R.id.button_start)
        val textik = findViewById<TextView>(R.id.textik)
        val slid = findViewById<Slider>(R.id.slider)

        circleProgress.max = 60

        slid.addOnChangeListener { _, _, _ ->
            textik.text = slid.value.toInt().toString()
        }



        fun main() {
            val updateProgress = {
                circleProgress.progress = currentProgress
            }

            currentProgress = slid.value.toInt()
            slid.isEnabled = false
            val scope = CoroutineScope(Dispatchers.Main)

            scope.launch {
                while (currentProgress > 0) {
                    currentProgress--
                    textik.text = currentProgress.toString()
                    updateProgress()
                }
                delay(1000)
            }
            //if (currentProgress == 0)
                //scope.cancel()
        }

        button.setOnClickListener {
            button.text = "STOP"
            main()
        }
    }
}