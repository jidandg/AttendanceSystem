package com.marwinjidopi.attendancesystem.ui.registerlogin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
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
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var imageFilePath: String
    lateinit var bitmap: Bitmap
    private var photoFile: File? = null
    private lateinit var photoURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding.imgCamera.setOnClickListener {
            dispatchTakePictureIntent()
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
                    uploadImage(bitmap, FirebaseAuth.getInstance().currentUser?.uid.toString())
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

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = getRotatedImage(photoURI)
            binding.imgCamera.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        } else {
            Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
        }

    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                photoFile = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
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

    private fun getRotatedImage(uri: Uri): Bitmap {
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        return when (contentResolver.openInputStream(uri)?.let { ExifInterface(it).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED) }) {
            ExifInterface.ORIENTATION_ROTATE_90 -> Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply { postRotate(90F) }, true)
            ExifInterface.ORIENTATION_ROTATE_180 -> Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply { postRotate(180F) }, true)
            ExifInterface.ORIENTATION_ROTATE_270 -> Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply { postRotate(270F) }, true)
            else -> bitmap
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