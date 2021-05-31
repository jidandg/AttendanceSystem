package com.marwinjidopi.attendancesystem.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.marwinjidopi.attendancesystem.MainActivity
import com.marwinjidopi.attendancesystem.data.User
import com.marwinjidopi.attendancesystem.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var activityBinding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        mAuth = FirebaseAuth.getInstance()

        activityBinding.btnLogin.setOnClickListener {
            val email = activityBinding.etEmail.text.toString().trim()
            val password = activityBinding.etPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    activityBinding.etEmail.error = "This field is required"
                    activityBinding.etEmail.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    activityBinding.etPassword.error = "This field is required"
                    activityBinding.etPassword.requestFocus()
                    return@setOnClickListener
                }
                password.length < 6 -> {
                    activityBinding.etPassword.error = "Password at least 6 character"
                    activityBinding.etPassword.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    activityBinding.btnLogin.visibility = View.INVISIBLE
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                // sign in success, move to home activity
                                Toast.makeText(this, "You're logged in", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finishAffinity()
                            } else {
                                activityBinding.btnLogin.visibility = View.VISIBLE
                                Toast.makeText(
                                    this,
                                    "Your E-Mail or Password are Wrong!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                }
            }
        }
        activityBinding.tvLogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }
}