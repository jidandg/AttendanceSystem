package com.marwinjidopi.attendancesystem.ui.notifications

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.marwinjidopi.attendancesystem.databinding.FragmentNotificationsBinding


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var database: FirebaseFirestore
    private lateinit var colUsers: CollectionReference
    private lateinit var colUserdata: CollectionReference
    private lateinit var docRef: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = FirebaseFirestore.getInstance()
        colUsers = database.collection("users")
        colUserdata = database.collection("userdata")

        docRef = FirebaseAuth.getInstance().currentUser?.uid.toString()

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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}