package com.capstoneproject.cvision.ui.splashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.capstoneproject.cvision.MainActivity
import com.capstoneproject.cvision.databinding.ActivitySplashScreenBinding
import com.capstoneproject.cvision.ui.onboarding.OnboardingFirstActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAnimate()

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launchWhenResumed {
                startActivity(Intent(this@SplashScreenActivity, OnboardingFirstActivity::class.java))
                finish()
            }
        }, 2000)
    }

    private fun setAnimate() {
        val image = ObjectAnimator.ofFloat(binding.iconApp, View.ALPHA, 1f).setDuration(400)
        val title = ObjectAnimator.ofFloat(binding.tvNameApk, View.ALPHA, 1f).setDuration(300)
        AnimatorSet().apply {
            playSequentially(image, title)
            start()
        }
    }
}