package com.capstoneproject.cvision.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.cvision.MainActivity
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.databinding.ActivityOnboardingSecondBinding

class OnboardingSecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingSecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStarted.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}