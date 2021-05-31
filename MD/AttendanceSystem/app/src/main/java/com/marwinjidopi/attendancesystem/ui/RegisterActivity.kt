package com.marwinjidopi.attendancesystem.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marwinjidopi.attendancesystem.data.User
import com.marwinjidopi.attendancesystem.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        registerBinding.btnSignUp.setOnClickListener {
            val username = registerBinding.etUsername.text.toString().trim()
            val email = registerBinding.etEmail.text.toString().trim()
            val password = registerBinding.etPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    registerBinding.etEmail.error = "This field is required"
                    registerBinding.etEmail.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    registerBinding.etPassword.error = "This field is required"
                    registerBinding.etPassword.requestFocus()
                    return@setOnClickListener
                }
                password.length < 6 -> {
                    registerBinding.etPassword.error = "Password at least 6 character"
                    registerBinding.etPassword.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val user = User(username , email, password)
                                database.collection("users")
                                    .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                                    .set(user)
                                    .addOnSuccessListener {
                                        Log.d(
                                            "RegisterActivity",
                                            "successfully Written!"
                                        )
                                    }
                                    .addOnFailureListener {
                                        Log.d(
                                            "RegisterActivity",
                                            "Error when write document!"
                                        )
                                    }
                                Toast.makeText(this, "Register successfully!", Toast.LENGTH_SHORT)
                                    .show()
                                startActivity(Intent(this, LoginActivity::class.java))
                            } else {
                                Toast.makeText(this, "Register failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
        registerBinding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}