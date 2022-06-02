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

    private var currentProgress: Int = 0
    val circleProgress = findViewById<ProgressBar>(R.id.progressBarCircular)
    val button = findViewById<Button>(R.id.button_start)
    val button2 = findViewById<Button>(R.id.button_stop)
    val textik = findViewById<TextView>(R.id.textik)
    val slid = findViewById<Slider>(R.id.slider)
    var isTimerRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

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
            if (!isTimerRun){
                if (circleProgress.progress>0) {
                    button.setText(R.string.text2)

                    Toast.makeText(this, "Время пошло!", Toast.LENGTH_SHORT).show()

                    //val handler = Handler(Looper.getMainLooper())
                }

            val scope = CoroutineScope(Dispatchers.Main)

                scope.launch {

                    currentProgress = 0
                    i = slid.value.toInt()
                    updateUI()
                    circleProgress.max = i

                    while (i > 0) {
                        i--
                        updateView(i)
                        currentProgress++
                        //textik.text = i.toString()
                        //updateProgressBar()
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
            }else{
                button.setText(R.string.text_one)
            }
            isTimerRun = !isTimerRun
            slid.isEnabled = !isTimerRun
        }
    }

    private suspend fun updateView(millis: Int){
        withContext(Dispatchers.Main){
            textik.text = millis.toString()
            circleProgress.progress = millis
            slid.isEnabled = !isTimerRun
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY, "string")
        super.onSaveInstanceState(outState)
    }
}