package edu.skillbox.timer

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
        val button2 = findViewById<Button>(R.id.button_stop)
        val textik = findViewById<TextView>(R.id.textik)
        val slid = findViewById<Slider>(R.id.slider)

        textik.text = "0"

        slid.addOnChangeListener { _, _, _ ->
            textik.text = slid.value.toInt().toString()
        }

        var i = 0

        fun main() {

            val updateProgress = {
                circleProgress.progress = currentProgress
            }

            val scope = CoroutineScope(Dispatchers.Main)

            scope.launch {
                currentProgress = 0
                i = slid.value.toInt()
                slid.isEnabled = false
                circleProgress.max = i
                button2.visibility = Button.VISIBLE
                button.visibility = Button.INVISIBLE
                updateProgress()
                while (i > 0) {

                    i--
                    currentProgress++
                    textik.text = i.toString()
                    updateProgress()
                    delay(1000)

                    if (i == 0) {
                        cancel()
                        button.visibility = Button.VISIBLE
                        button2.visibility = Button.INVISIBLE
                        slid.isEnabled = true
                        textik.text = slid.value.toInt().toString()
                        circleProgress.progress = 0
                        circleProgress.progressDrawable
                    }
                }
            }

            button2.setOnClickListener {
                button.visibility = Button.VISIBLE
                button2.visibility = Button.INVISIBLE
                slid.isEnabled = true
                textik.text = slid.value.toInt().toString()
                circleProgress.progress = 0
                scope.cancel()
                Toast.makeText(this, "Время вышло!", Toast.LENGTH_SHORT).show()
            }
        }

        button.setOnClickListener {
            Toast.makeText(this, "Время пошло!", Toast.LENGTH_SHORT).show()
            main()
            button2.visibility = Button.VISIBLE
            button.visibility = Button.INVISIBLE
        }
    }
}