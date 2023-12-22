package com.capstoneproject.cvision.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.cvision.databinding.ActivityCreditsBinding

class CreditsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreditsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}