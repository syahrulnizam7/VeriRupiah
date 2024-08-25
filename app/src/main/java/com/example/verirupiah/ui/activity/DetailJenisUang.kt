package com.example.verirupiah.ui.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.verirupiah.R
import com.example.verirupiah.databinding.ActivityDetailJenisUangBinding
import com.example.verirupiah.ui.adapter.ImagePagerAdapter

class DetailJenisUang : AppCompatActivity() {
    private lateinit var binding: ActivityDetailJenisUangBinding
    private lateinit var indicator1: View
    private lateinit var indicator2: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailJenisUangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the action bar
        setSupportActionBar(binding.toolbar)

        // Set custom title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarTitle.text = "Detail Nominal Uang"

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Mengatur warna ikon secara dinamis
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        // Initialize indicators
        indicator1 = findViewById(R.id.indicator1)
        indicator2 = findViewById(R.id.indicator2)

        // Handle incoming intent data
        val intent = intent
        if (intent != null) {
            val nama_uang = intent.getStringExtra("nama_uang")
            val tahun_emisi = intent.getStringExtra("tahun_emisi")
            val deskripsi_jenisuang = intent.getIntExtra("deskripsi_jenisuang", R.string.desc_seratus)
            val gambar = intent.getIntExtra("gambar", R.drawable.duit_seratus)
            val gambar_belakang = intent.getIntExtra("gambar_belakang", R.drawable.duit_seratus_2)

            binding.detailNamauang.text = nama_uang
            binding.detailTahunemisi.text = tahun_emisi
            binding.detailDeskripsiJenisuang.setText(deskripsi_jenisuang)

            // Setup ViewPager2 with images
            val images = listOf(gambar, gambar_belakang)
            val adapter = ImagePagerAdapter(images)
            binding.viewPager.adapter = adapter

            // Set up PageChangeCallback
            binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    updateIndicators(position)
                }
            })

            // Initialize indicators
            updateIndicators(0) // Set initial indicator state
        }
    }

    private fun updateIndicators(position: Int) {
        when (position) {
            0 -> {
                indicator1.background = ContextCompat.getDrawable(this, R.drawable.indicator_selected)
                indicator2.background = ContextCompat.getDrawable(this, R.drawable.indicator_unselected)
            }
            1 -> {
                indicator1.background = ContextCompat.getDrawable(this, R.drawable.indicator_unselected)
                indicator2.background = ContextCompat.getDrawable(this, R.drawable.indicator_selected)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
