package com.marwinjidopi.attendancesystem.ui.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.marwinjidopi.attendancesystem.data.AbsentForm
import com.marwinjidopi.attendancesystem.databinding.ActivitySendDataBinding
import com.marwinjidopi.attendancesystem.ui.detail.DetailActivity.Companion.globalData
import com.marwinjidopi.attendancesystem.ui.login.LoginActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class SendDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendDataBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private val IMAGE_CAPTURE = 1
    lateinit var imageFilePath: String
    lateinit var bitmap: Bitmap
    private var photoFile: File? = null
    private lateinit var photoURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        binding.imgCameraSendData.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.btnSendData.setOnClickListener {
            val data = AbsentForm(bitmap.toString())
            uploadImage(bitmap, FirebaseAuth.getInstance().currentUser?.uid.toString())
            database.collection("userAbsent")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .set(data)
                .addOnSuccessListener {
                    Log.d(
                        "SendDataActivity",
                        "successfully Written!"
                    )
                }
                .addOnFailureListener {
                    Log.d(
                        "SendDataActivity",
                        "Error when write document!"
                    )
                }
            Toast.makeText(this, "Upload successfully!", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, DetailActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = getRotatedImage(photoURI)
            binding.imgCameraSendData.setImageBitmap(imageBitmap)
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
                    startActivityForResult(takePictureIntent, IMAGE_CAPTURE)
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getRotatedImage(uri: Uri): Bitmap {
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        return when (contentResolver.openInputStream(uri)?.let {
            ExifInterface(it).getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
        }) {
            ExifInterface.ORIENTATION_ROTATE_90 -> Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                Matrix().apply { postRotate(90F) },
                true
            )
            ExifInterface.ORIENTATION_ROTATE_180 -> Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                Matrix().apply { postRotate(180F) },
                true
            )
            ExifInterface.ORIENTATION_ROTATE_270 -> Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                Matrix().apply { postRotate(270F) },
                true
            )
            else -> bitmap
        }
    }

    private fun uploadImage(img: Bitmap, pictName: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs://attendance-system-9f194.appspot.com")
        val imagePath = "${pictName + globalData}.jpg"
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val imageRef = storageRef.child("imgDataAbsent/$userId/$imagePath")
        val byteArrayOutputStream = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        }
    }
}