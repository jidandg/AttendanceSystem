package com.marwinjidopi.attendancesystem.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marwinjidopi.attendancesystem.data.entity.ClassEntity
import com.marwinjidopi.attendancesystem.data.entity.ContentEntity
import com.marwinjidopi.attendancesystem.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private var data: ArrayList<ClassEntity> = ArrayList()
    private lateinit var detailAdapter: DetailAdapter

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
                populateData(viewModel.getData())
            }
        }

        recyclerLastClass()
        recyclerNowClass()
        recyclerNextClass()

        binding.btnAbsentClass.setOnClickListener {
            val intent = Intent(this, SendDataActivity::class.java)
            startActivity(intent)
        }
    }

    private fun recyclerNextClass() {
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        val data = viewModel.getNextClass()
        val homeAdapter = DetailAdapter()
        homeAdapter.setData(data)
        with(binding.rvNextClass){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    private fun recyclerNowClass() {
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        val data = viewModel.getNowClass()
        val homeAdapter = DetailAdapter()
        homeAdapter.setData(data)
        with(binding.rvOnGoingClass){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    private fun recyclerLastClass() {
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        val data = viewModel.getLastClass()
        val homeAdapter = DetailAdapter()
        homeAdapter.setData(data)
        with(binding.rvLastClass){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateData(data: ContentEntity) {
        binding.tvDetailClassName.text = data.className
        binding.tvDetailClassTeacher.text = data.classTeacherI + " | " + data.classTeacherII
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