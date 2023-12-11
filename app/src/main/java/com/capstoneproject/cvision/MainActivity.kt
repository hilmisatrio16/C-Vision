package com.capstoneproject.cvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.marginBottom
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstoneproject.cvision.databinding.ActivityMainBinding
import com.capstoneproject.cvision.ui.diagnose.DetectionActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //supaya warna icon nya bisa diset manual
//        binding.bottomNav.itemIconTintList = null
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

//        val navController = findNavController(androidx.navigation.fragment.R.id.nav_host_fragment_container)
        binding.bottomNav.setupWithNavController(navController)
        binding.scan.setOnClickListener {
            startActivity(Intent(this, DetectionActivity::class.java))
        }

    }
}