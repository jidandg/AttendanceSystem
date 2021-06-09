package com.marwinjidopi.attendancesystem.ui.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.marwinjidopi.attendancesystem.databinding.FragmentProfileBinding
import com.marwinjidopi.attendancesystem.ui.forgot_password.ForgotPasswordActivity
import com.marwinjidopi.attendancesystem.ui.login.LoginActivity
import com.marwinjidopi.attendancesystem.utils.GlideApp

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var colUsers: CollectionReference
    private lateinit var colUserdata: CollectionReference
    private lateinit var docRef: String

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        colUsers = database.collection("users")
        colUserdata = database.collection("userdata")

        val storage = FirebaseStorage.getInstance()
        val userId = mAuth.currentUser?.uid.toString()
        val imagePath = "${userId}.jpg"
        val storageRef = storage.getReferenceFromUrl("gs://attendance-system-9f194.appspot.com/img/$userId/$imagePath")

        docRef = mAuth.currentUser?.uid.toString()

        context?.let {
            GlideApp.with(it)
                .load(storageRef)
                .into(binding.imgProfile)
        }

        colUsers
            .document(docRef)
            .get()
            .addOnSuccessListener { document ->
                binding.tvUsername.text = document.data?.getValue("username").toString()
                binding.tvEmailPreview.text = document.data?.getValue("email").toString()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        colUserdata
            .document(docRef)
            .get()
            .addOnSuccessListener { document ->
                binding.tvNamePreview.text = document.data?.getValue("name").toString()
                binding.tvNIMPreview.text = document.data?.getValue("nim").toString()
                binding.tvSemesterPreview.text = document.data?.getValue("semester").toString()
                binding.tvFacultyPreview.text = document.data?.getValue("faculty").toString()
                binding.tvMajorPreview.text = document.data?.getValue("major").toString()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        binding.tvChangePassword.setOnClickListener {
            val intent = Intent(activity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            logOut()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logOut() {
        mAuth.signOut()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
