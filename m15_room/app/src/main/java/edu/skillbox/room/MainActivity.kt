package edu.skillbox.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import edu.skillbox.room.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val userDao = (application as App).db.userDao()
                return MainViewModel(userDao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.addTextChangedListener { charSequence ->
            val str = charSequence.toString()
            val k = Regex("^[a-z-]*$", RegexOption.IGNORE_CASE)

            if (!k.matches(str)) {
                Toast.makeText(
                    this,
                    "Введенное слово нельзя добавить в словарь",
                    Toast.LENGTH_SHORT
                ).show()
                binding.addBtn.isEnabled = false
            } else {
                binding.addBtn.isEnabled = true
                binding.addBtn.setOnClickListener {
                    if (viewModel.allWords.value.equals(str)) {
                        viewModel.onUpdate(viewModel.allWords.value.first().word)

                    } else if (viewModel.allWords.value.first().word.isEmpty() || viewModel.allWords.value.first().word != str){
                        viewModel.onAddBtn(str)
                    }
                }
            }

        }

        binding.deletebtn.setOnClickListener { viewModel.onDeleteBtn() }

        lifecycleScope.launchWhenStarted {
            viewModel.allWords
                .collect { words ->
                    binding.textView.text = words.joinToString(separator = "\r\n")
                }
        }
    }
}