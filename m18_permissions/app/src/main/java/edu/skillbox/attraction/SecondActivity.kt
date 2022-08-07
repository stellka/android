package edu.skillbox.attraction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import edu.skillbox.attraction.databinding.ActivityMainBinding
import edu.skillbox.attraction.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private val viewModel: AccessViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val photoDao = (application as App).db.photoDao()
                return AccessViewModel(photoDao) as T
            }
        }
    }

    private lateinit var binding: ActivitySecondBinding
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL,false)

        lifecycleScope.launchWhenStarted {
            viewModel.allPhotos
                .collect { photos ->
                    adapter.setData(photos)
                }
        }

        binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }
}