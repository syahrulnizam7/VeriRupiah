package com.example.verirupiah.ui.activity


import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import com.example.verirupiah.R
import com.example.verirupiah.databinding.ActivitySejarahBinding

class SejarahActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySejarahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySejarahBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Set the toolbar as the action bar
        setSupportActionBar(binding.toolbar)

        // Hide default title text
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set custom title
        binding.toolbarTitle.text = "Sejarah Uang"

        // Mengatur warna ikon secara dinamis
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        // Ambil URL Wikipedia yang akan ditampilkan (contoh: Wikipedia tentang uang)
        val wikipediaUrl = "https://id.wikipedia.org/wiki/Rupiah"

        // Inisialisasi WebView
        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(wikipediaUrl)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
