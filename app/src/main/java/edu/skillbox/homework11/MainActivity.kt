package edu.skillbox.homework11

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import edu.skillbox.homework11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener{
            repository.saveText(binding.editText.text.toString(), this)
            val textFromRepository = repository.getText(this)
            binding.textForLoading.text = textFromRepository
        }

        binding.buttonClear.setOnClickListener {
            repository.clearText(this)
            val textFromRepository = repository.getText(this)
            binding.textForLoading.text = textFromRepository
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelable("Repository_text", repository)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getParcelable<Repository>("Repository_text")
    }
}