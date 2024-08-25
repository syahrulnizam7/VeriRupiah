package com.example.verirupiah.ui.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.verirupiah.R
import com.example.verirupiah.databinding.ActivityEdukasiWebViewBinding

class EdukasiWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdukasiWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdukasiWebViewBinding.inflate(layoutInflater)
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


        // Get the URL passed from the intent
        val url = intent.getStringExtra("url")

        // Set up the WebView
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url ?: "https://www.example.com")
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}