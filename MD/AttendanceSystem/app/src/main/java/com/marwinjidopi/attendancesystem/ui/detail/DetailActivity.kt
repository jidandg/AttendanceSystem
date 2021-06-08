package com.marwinjidopi.attendancesystem.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marwinjidopi.attendancesystem.data.ContentEntity
import com.marwinjidopi.attendancesystem.databinding.ActivityDetailBinding
import java.io.File

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private val IMAGE_CAPTURE = 1
    lateinit var imageFilePath: String
    lateinit var bitmap: Bitmap
    private var photoFile: File? = null
    private lateinit var photoURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        val extra = intent.extras
        if (extra != null) {
            val id = extra.getString(EXTRA_CONTENT)
            if (id != null) {
                viewModel.setSelectedData(id)
                populateContent(viewModel.getData())
            }
        }

        binding.btnAbsentClass.setOnClickListener {
            val intent = Intent(this, SendDataActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateContent(data: ContentEntity) {
        binding.tvDetailClassName.text = data.className
        binding.tvDetailClassTeacher.text = data.classTeacherI + data.classTeacherII
        binding.tvDetailClassDate.text = data.classDate
        binding.tvDetailClassTime.text = data.classTime
        binding.tvDetailClassInfo.text = data.classInfo
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_CONTENT = "EXTRA_CONTENT"
    }
}