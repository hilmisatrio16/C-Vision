package com.capstoneproject.cvision.ui.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstoneproject.cvision.databinding.ActivityOnboardingFirstBinding

class OnboardingFirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAnimation()
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, OnboardingSecondActivity::class.java))
        }
    }

    private fun setAnimation() {
        val image = ObjectAnimator.ofFloat(binding.imgBanner, View.ALPHA, 1f).setDuration(400)
        val greet = ObjectAnimator.ofFloat(binding.tvGreeting, View.ALPHA, 1f).setDuration(300)
        val description = ObjectAnimator.ofFloat(binding.tvDescription, View.ALPHA, 1f).setDuration(300)
        val button = ObjectAnimator.ofFloat(binding.btnNext, View.ALPHA, 1f).setDuration(300)
        AnimatorSet().apply {
            playSequentially(image, greet, description, button)
            start()
        }
    }
}