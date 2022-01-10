package edu.skillbox.layoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.skillbox.layoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customButton.setTextOne("верхняя строчка, настроенная из кода")

        binding.customButton.setTextTwo("нижняя строчка, настроенная из кода")
    }
}