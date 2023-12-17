package com.capstoneproject.cvision.ui.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstoneproject.cvision.MainActivity
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.databinding.ActivityOnboardingSecondBinding

class OnboardingSecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAnimation()
        binding.btnStarted.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun setAnimation() {
        val image = ObjectAnimator.ofFloat(binding.imageBanner, View.ALPHA, 1f).setDuration(400)
        val description = ObjectAnimator.ofFloat(binding.tvDescription, View.ALPHA, 1f).setDuration(300)
        val button = ObjectAnimator.ofFloat(binding.btnStarted, View.ALPHA, 1f).setDuration(300)
        AnimatorSet().apply {
            playSequentially(image,description, button)
            start()
        }
    }
}