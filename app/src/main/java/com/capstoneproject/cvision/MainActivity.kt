package com.capstoneproject.cvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.cvision.databinding.ActivityMainBinding
import com.capstoneproject.cvision.ui.diagnose.DetectionActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scan.setOnClickListener {
            startActivity(Intent(this, DetectionActivity::class.java))
        }
    }
}