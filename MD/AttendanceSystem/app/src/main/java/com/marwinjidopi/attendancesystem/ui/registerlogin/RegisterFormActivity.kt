package com.marwinjidopi.attendancesystem.ui.registerlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marwinjidopi.attendancesystem.data.User
import com.marwinjidopi.attendancesystem.data.UserForm
import com.marwinjidopi.attendancesystem.databinding.ActivityRegisterBinding
import com.marwinjidopi.attendancesystem.databinding.ActivityRegisterFormBinding

class RegisterFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterFormBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding.btnSend.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val nim = binding.etNIM.text.toString().trim()
            val semester = binding.etSemester.text.toString().trim()
            val faculty = binding.etFaculty.text.toString().trim()
            val major = binding.etMajor.text.toString().trim()

            when {
                name.isEmpty() -> {
                    binding.etName.error = "This field is required"
                    binding.etName.requestFocus()
                    return@setOnClickListener
                }
                nim.isEmpty() -> {
                    binding.etNIM.error = "This field is required"
                    binding.etNIM.requestFocus()
                    return@setOnClickListener
                }
                semester.isEmpty() -> {
                    binding.etSemester.error = "This field is required"
                    binding.etSemester.requestFocus()
                    return@setOnClickListener
                }
                faculty.isEmpty() -> {
                    binding.etFaculty.error = "This field is required"
                    binding.etFaculty.requestFocus()
                    return@setOnClickListener
                }
                major.isEmpty() -> {
                    binding.etMajor.error = "This field is required"
                    binding.etMajor.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    val user = UserForm(name, nim, semester, faculty, major)
                    database.collection("userdata")
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
                }
            }
        }
    }
}