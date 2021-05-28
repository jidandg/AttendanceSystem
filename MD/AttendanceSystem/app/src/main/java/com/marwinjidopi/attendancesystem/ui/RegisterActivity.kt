package com.marwinjidopi.attendancesystem.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marwinjidopi.attendancesystem.R
import com.marwinjidopi.attendancesystem.databinding.ActivityLoginBinding
import com.marwinjidopi.attendancesystem.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.btnSignUp.setOnClickListener {
            val email: String = registerBinding.etEmail.text.toString().trim()
            val password: String = registerBinding.etPassword.text.toString().trim()

            if (email.isEmpty()){
                registerBinding.etEmail.error = "Email Invalid"
                registerBinding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty() || password.length < 6) {
                registerBinding.etPassword.error = "Password Must have At Least 7 Characters"
                registerBinding.etPassword.requestFocus()
                return@setOnClickListener
            }
        }
        registerBinding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}