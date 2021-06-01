package com.marwinjidopi.attendancesystem.ui.registerlogin

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

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    binding.etEmail.error = "This field is required"
                    binding.etEmail.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.etPassword.error = "This field is required"
                    binding.etPassword.requestFocus()
                    return@setOnClickListener
                }
                password.length < 6 -> {
                    binding.etPassword.error = "Password at least 6 character"
                    binding.etPassword.requestFocus()
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
                                startActivity(Intent(this, RegisterFormActivity::class.java))
                            } else {
                                Toast.makeText(this, "Register failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}