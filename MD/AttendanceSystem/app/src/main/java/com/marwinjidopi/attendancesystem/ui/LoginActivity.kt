package com.marwinjidopi.attendancesystem.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marwinjidopi.attendancesystem.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var activityBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        activityBinding.btnLogin.setOnClickListener {
            val email: String = activityBinding.etEmail.text.toString().trim()
            val password: String = activityBinding.etPassword.text.toString().trim()

            if (email.isEmpty()){
                activityBinding.etEmail.error = "Email Invalid"
                activityBinding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty() || password.length < 6) {
                activityBinding.etPassword.error = "Password Must have At Least 7 Characters"
                activityBinding.etPassword.requestFocus()
                return@setOnClickListener
            }
        }
        activityBinding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}