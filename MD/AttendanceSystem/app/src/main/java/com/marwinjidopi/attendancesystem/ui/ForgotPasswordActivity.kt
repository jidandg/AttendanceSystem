package com.marwinjidopi.attendancesystem.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.marwinjidopi.attendancesystem.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btnReset.setOnClickListener {
            val email = binding.etEmailForgotPass.text.toString()

            if (email.isEmpty()) {
                binding.etEmailForgotPass.error = "Email is required"
                binding.etEmailForgotPass.requestFocus()
            } else {
                mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Check your email address", Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }
}