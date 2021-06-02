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
import com.marwinjidopi.attendancesystem.data.UserForm
import com.marwinjidopi.attendancesystem.databinding.ActivityRegisterFormBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class RegisterFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterFormBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    val REQUEST_IMAGE_CAPTURE = 0
    lateinit var imageFilePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding.imgCamera.setOnClickListener {
            try {
                val imageFile = createImageFile()
                val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (callCameraIntent.resolveActivity(packageManager) != null) {
                    val authorities = "$packageName.fileprovider"
                    val imageUri = FileProvider.getUriForFile(this, authorities, imageFile)
                    callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    startActivityForResult(callCameraIntent, REQUEST_IMAGE_CAPTURE)
                }
            } catch (e: IOException) {
                Toast.makeText(this, "Could not create file!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSend.setOnClickListener {
            //val img = binding.imgCamera.setImageURI().toString().trim()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
/*                if(resultCode == Activity.RESULT_OK && data != null) {
                    binding.imgCamera.setImageBitmap(data.extras.get("data") as Bitmap)
                }*/
                if (resultCode == Activity.RESULT_OK) {
                    binding.imgCamera.setImageBitmap(setScaledBitmap())
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName: String = "JPEG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if(!storageDir?.exists()!!) storageDir.mkdirs()
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = imageFile.absolutePath
        return imageFile
    }

    fun setScaledBitmap(): Bitmap {
        val imageViewWidth = binding.imgCamera.width
        val imageViewHeight = binding.imgCamera.height

        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFilePath, bmOptions)
        val bitmapWidth = bmOptions.outWidth
        val bitmapHeight = bmOptions.outHeight

        val scaleFactor = Math.min(bitmapWidth/imageViewWidth, bitmapHeight/imageViewHeight)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        return BitmapFactory.decodeFile(imageFilePath, bmOptions)

    }
}