package com.capstoneproject.cvision.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.cvision.databinding.ActivityOnboardingFirstBinding

class OnboardingFirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, OnboardingSecondActivity::class.java))
            finish()
        }
    }
}