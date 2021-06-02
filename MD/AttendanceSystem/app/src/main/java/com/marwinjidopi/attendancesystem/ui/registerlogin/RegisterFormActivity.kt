package com.marwinjidopi.attendancesystem.ui.registerlogin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.marwinjidopi.attendancesystem.data.UserForm
import com.marwinjidopi.attendancesystem.databinding.ActivityRegisterFormBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.io.File.createTempFile as createTempFile1

@Suppress("DEPRECATION")
class RegisterFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterFormBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    val REQUEST_IMAGE_CAPTURE = 0
    lateinit var imageFilePath: String
    lateinit var bitmap: Bitmap
    private var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding.imgCamera.setOnClickListener {

            dispatchTakePictureIntent()
//            try {
////                val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
////                if (callCameraIntent.resolveActivity(packageManager) != null) {
////                    val authorities = "$packageName.fileprovider"
////                    val imageUri = FileProvider.getUriForFile(this, authorities, imageFile)
////                    callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
////                    startActivityForResult(callCameraIntent, REQUEST_IMAGE_CAPTURE)
//                }
//            } catch (e: IOException) {
//                Toast.makeText(this, "Could not create file!", Toast.LENGTH_SHORT).show()
//            }
        }

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
                    uploadImage(bitmap, "asd")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val imageBitmap = BitmapFactory.decodeFile(photoFile?.absolutePath)
                    binding.imgCamera.setImageBitmap(imageBitmap)
                    bitmap = imageBitmap
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "$packageName.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            imageFilePath = absolutePath
        }
    }

    private fun uploadImage(img: Bitmap, pictName: String) {
        val storage = FirebaseStorage.getInstance()
        val storgaRef = storage.getReferenceFromUrl("gs://attendance-system-9f194.appspot.com")
        val imagePath = "${pictName}.jpg"
        val imageRef = storgaRef.child("img/$imagePath")
        val baos = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        }
    }
}