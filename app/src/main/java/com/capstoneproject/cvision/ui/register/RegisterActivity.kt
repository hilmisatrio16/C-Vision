package com.capstoneproject.cvision.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.capstoneproject.cvision.databinding.ActivityRegisterBinding
import com.capstoneproject.cvision.ui.login.LoginActivity
import com.capstoneproject.cvision.utils.Result

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val registerVM by viewModels<RegisterViewModel> {
        RegisterViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSignup.setOnClickListener {
            registerUser()
        }

        binding.buttonSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


    }

    private fun registerUser() {
        val name = binding.inputName.text.toString()
        val username = binding.inputUsername.text.toString()
        val password = binding.inputPassword.text.toString()

        if(name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()){
            registerVM.register(name, username, password).observe(this, Observer {
                if (it != null){
                    when(it){
                        is Result.Success ->{
                            binding.lottieLoading.visibility = View.GONE
                            Log.d("RESPONSE AUTH", it.data.toString())
                            Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                            clearField()
                            startActivity(Intent(this, LoginActivity::class.java))

                        }
                        is Result.Loading -> {
                            binding.lottieLoading.visibility = View.VISIBLE
                            Log.d("RESPONSE AUTH", "load")
                        }

                        is  Result.Error -> {
                            binding.lottieLoading.visibility = View.GONE
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                            Log.d("RESPONSE AUTH", it.error)
                        }
                    }
                }
            })
        }else{
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearField() {
        binding.inputName.setText("")
        binding.inputUsername.setText("")
        binding.inputPassword.setText("")
    }
}