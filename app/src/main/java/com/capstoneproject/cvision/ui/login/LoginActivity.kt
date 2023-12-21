package com.capstoneproject.cvision.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.capstoneproject.cvision.MainActivity
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.databinding.ActivityLoginBinding
import com.capstoneproject.cvision.ui.register.RegisterActivity
import com.capstoneproject.cvision.utils.Result

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginVM by viewModels<LoginViewModel> {
        LoginViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.buttonSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginVM.getTokenUser().observe(this, Observer {
            if(it.isNotEmpty()){
                val intent = Intent(this, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        })
    }

    private fun loginUser() {
        val username = binding.inputUsername.text.toString()
        val password = binding.inputPassword.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            loginVM.login(username, password).observe(this, Observer {
                if (it != null) {
                    when (it) {
                        is Result.Loading -> {
                            Log.d("RESPONSE AUTH", "load")
                        }

                        is Result.Success -> {
                            if(!it.data.error){
                                loginVM.sessionIsActive(it.data.token, it.data.yourName)
                                clearField()
                                Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                                Log.d("RESPONSE AUTH", it.data.toString())
                            }else{
                                Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                            }

                        }

                        is Result.Error -> {
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                            Log.d("RESPONSE AUTH", it.error)
                        }
                    }
                }
            })
        }
    }

    private fun clearField() {
        binding.inputUsername.setText("")
        binding.inputPassword.setText("")
    }
}