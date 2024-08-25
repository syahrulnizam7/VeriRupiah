package com.example.verirupiah.ui.activity

import android.graphics.PorterDuff
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.verirupiah.R
import com.example.verirupiah.databinding.ActivityDetailCiriBinding

class DetailCiriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailCiriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCiriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the action bar
        setSupportActionBar(binding.toolbar)

        // Hide default title text
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set custom title
        binding.toolbarTitle.text = "Ciri Uang"

        // Mengatur warna ikon secara dinamis
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(
            ContextCompat.getColor(this, android.R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        // Get intent extras
        val nominal = intent.getStringExtra("NOMINAL")
        val tahunEmisi = intent.getStringExtra("TAHUN_EMISI")
        val gambarDepan = intent.getIntExtra("GAMBAR_DEPAN", 0)
        val gambarBelakang = intent.getIntExtra("GAMBAR_BELAKANG", 0)
        val gambarUVDepan = intent.getIntExtra("GAMBAR_UV_DEPAN", 0)
        val gambarUVBelakang = intent.getIntExtra("GAMBAR_UV_BELAKANG", 0)

        // Set values to views
        binding.tvNominal.text = nominal
        binding.tvTahunEmisi.text = tahunEmisi
        binding.imgDepan.setImageResource(gambarDepan)
        binding.imgBelakang.setImageResource(gambarBelakang)
        binding.imgDepanUV.setImageResource(gambarUVDepan)
        binding.imgBelakangUV.setImageResource(gambarUVBelakang)

        // Initialize the UV image views
        val imgDepanUV = binding.imgDepanUV
        val imgBelakangUV = binding.imgBelakangUV

        // Initialize the SeekBars
        val seekBarDepan = binding.seekBarDepan
        val seekBarBelakang = binding.seekBarBelakang

        // Handle the front image sliding transition
        seekBarDepan.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Calculate the clipping area
                val width = imgDepanUV.width
                val clipBounds = Rect(0, 0, (width * (progress / 100f)).toInt(), imgDepanUV.height)
                imgDepanUV.clipBounds = clipBounds
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Optional: handle the start of the touch event
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Optional: handle the end of the touch event
            }
        })

        // Handle the back image sliding transition
        seekBarBelakang.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Calculate the clipping area
                val width = imgBelakangUV.width
                val clipBounds = Rect(0, 0, (width * (progress / 100f)).toInt(), imgBelakangUV.height)
                imgBelakangUV.clipBounds = clipBounds
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Optional: handle the start of the touch event
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Optional: handle the end of the touch event
            }
        })

        // Ensure the UV images are initially clipped completely
        imgDepanUV.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            imgDepanUV.clipBounds = Rect(0, 0, 0, imgDepanUV.height)
        }
        imgBelakangUV.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            imgBelakangUV.clipBounds = Rect(0, 0, 0, imgBelakangUV.height)
        }

        // Initialize and set click listener for the button
        val btnCiriUangLebihLanjut = binding.btnCiriUangLebihLanjut
        btnCiriUangLebihLanjut.setOnClickListener {
            val dialog = CiriActivity(this, "ciri_uang.pdf")
            dialog.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
