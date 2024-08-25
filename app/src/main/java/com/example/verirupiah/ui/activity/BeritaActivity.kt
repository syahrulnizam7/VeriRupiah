package com.example.verirupiah.ui.activity

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.verirupiah.R
import com.example.verirupiah.databinding.ActivityBeritaBinding

class BeritaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeritaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeritaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the action bar
        setSupportActionBar(binding.toolbar)

        // Hide default title text
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set custom title
        binding.toolbarTitle.text = "Edukasi"

        // Mengatur warna ikon secara dinamis
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        // URLs for preview images
        val previewUrl1 = "https://thumbalizr.com/api/v1/embed/63Z5xxrNeCUiTLXGyVKI6D4dyAfJ9/13e3d455932a9467456ec5d74e11a3cf/?url=https://www.linggapura.desa.id/artikel/2024/06/20/cara-membedakan-mana-uang-asli-dan-palsu-apa-saja-ciri-uang-palsu/&size=screen&delay=0&format=png&width=1280&height=1024&quality=90#finished"
        val previewUrl2 = "https://thumbalizr.com/api/v1/embed/63Z5xxrNeCUiTLXGyVKI6D4dyAfJ9/45ebcb51c97237061fca228c2eb65f66/?url=https://www.bi.go.id/id/rupiah/pencegahan-rupiah-palsu/Default.aspx&size=screen&delay=0&format=png&width=1280&height=1024&quality=90"
        val previewUrl3 = "https://thumbalizr.com/api/v1/embed/63Z5xxrNeCUiTLXGyVKI6D4dyAfJ9/8172c6fec031869e63077afa48a6ae7b/?url=https://www.antaranews.com/berita/4158789/ini-tips-terhindar-dari-uang-palsu&size=screen&delay=0&format=png&width=1280&height=1024&quality=90#finished"

        // Load the preview images using Glide
        Glide.with(this).load(previewUrl1).into(binding.blog1Image)
        Glide.with(this).load(previewUrl2).into(binding.blog2Image)
        Glide.with(this).load(previewUrl3).into(binding.blog3Image)

        // Set click listeners on each blog
        binding.blog1Image.setOnClickListener {
            openWebView("https://www.linggapura.desa.id/artikel/2024/06/20/cara-membedakan-mana-uang-asli-dan-palsu-apa-saja-ciri-uang-palsu/")
        }

        binding.blog2Image.setOnClickListener {
            openWebView("https://www.bi.go.id/id/rupiah/pencegahan-rupiah-palsu/Default.aspx")
        }

        binding.blog3Image.setOnClickListener {
            openWebView("https://www.antaranews.com/berita/4158789/ini-tips-terhindar-dari-uang-palsu")
        }
    }

    // Function to open WebViewActivity with the provided URL
    private fun openWebView(url: String) {
        val intent = Intent(this, EdukasiWebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
