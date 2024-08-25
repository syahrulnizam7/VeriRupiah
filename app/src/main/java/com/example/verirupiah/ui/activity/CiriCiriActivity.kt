package com.example.verirupiah.ui.activity

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.verirupiah.R
import com.example.verirupiah.databinding.ActivityCiriCiriBinding
import com.example.verirupiah.databinding.ActivitySejarahBinding

class CiriCiriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCiriCiriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCiriCiriBinding.inflate(layoutInflater)
        setContentView(binding.root)

// Ambil referensi dari FrameLayouts
        val frame100K2022 = findViewById<FrameLayout>(R.id.frame_100k_2022)
        val frame100K2016 = findViewById<FrameLayout>(R.id.frame_100k_2016)
        val frame50K2022 = findViewById<FrameLayout>(R.id.frame_50k_2022)
        val frame50K2016 = findViewById<FrameLayout>(R.id.frame_50k_2016)
        val frame20K2022 = findViewById<FrameLayout>(R.id.frame_20k_2022)
        val frame20K2016 = findViewById<FrameLayout>(R.id.frame_20k_2016)
        val frame10K2022 = findViewById<FrameLayout>(R.id.frame_10k_2022)
        val frame10K2016 = findViewById<FrameLayout>(R.id.frame_10k_2016)
        val frame5K2022 = findViewById<FrameLayout>(R.id.frame_5k_2022)
        val frame5K2016 = findViewById<FrameLayout>(R.id.frame_5k_2016)
        val frame2K2022 = findViewById<FrameLayout>(R.id.frame_2k_2022)
        val frame2K2016 = findViewById<FrameLayout>(R.id.frame_2k_2016)
        val frame1K2022 = findViewById<FrameLayout>(R.id.frame_1k_2022)
        val frame1K2016 = findViewById<FrameLayout>(R.id.frame_1k_2016)

        // Tambahkan klik listener
        frame100K2022.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 100.000")
            intent.putExtra("TAHUN_EMISI", "TE 2022")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_seratus_2022)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_seratus_2022_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_seratus_2022_uv)
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_seratus_2022_2_uv)
            startActivity(intent)
        }

        frame100K2016.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 100.000")
            intent.putExtra("TAHUN_EMISI", "TE 2016")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_seratus)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_seratus_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_seratus_uv) // Tambahkan gambar UV untuk 2016
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_seratus_2_uv) // Tambahkan gambar UV untuk 2016
            startActivity(intent)
        }

        frame50K2022.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 50.000")
            intent.putExtra("TAHUN_EMISI", "TE 2022")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_limapuluh_2022)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_limapuluh_2022_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_limapuluh_2022_uv)
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_limapuluh_2022_2_uv)
            startActivity(intent)
        }

        frame50K2016.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 50.000")
            intent.putExtra("TAHUN_EMISI", "TE 2016")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_limapuluh)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_limapuluh_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_limapuluh_uv) // Tambahkan gambar UV untuk 2016
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_limapuluh_2_uv) // Tambahkan gambar UV untuk 2016
            startActivity(intent)
        }

        frame20K2022.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 20.000")
            intent.putExtra("TAHUN_EMISI", "TE 2022")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_duapuluh_2022)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_duapuluh_2022_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_duapuluh_2022_uv)
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_duapuluh_2022_2_uv)
            startActivity(intent)
        }

        frame20K2016.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 20.000")
            intent.putExtra("TAHUN_EMISI", "TE 2016")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_duapuluh)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_duapuluh_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_duapuluh_uv) // Tambahkan gambar UV untuk 2016
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_duapuluh_2_uv) // Tambahkan gambar UV untuk 2016
            startActivity(intent)
        }

        frame10K2022.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 10.000")
            intent.putExtra("TAHUN_EMISI", "TE 2022")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_sepuluh_2022)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_sepuluh_2022_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_sepuluh_2022_uv)
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_sepuluh_2022_2_uv)
            startActivity(intent)
        }

        frame10K2016.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 10.000")
            intent.putExtra("TAHUN_EMISI", "TE 2016")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_sepuluh)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_sepuluh_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_sepuluh_uv) // Tambahkan gambar UV untuk 2016
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_sepuluh_2_uv) // Tambahkan gambar UV untuk 2016
            startActivity(intent)
        }

        frame5K2022.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 5.000")
            intent.putExtra("TAHUN_EMISI", "TE 2022")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_limaribu_2022)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_limaribu_2022_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_limaribu_2022_uv)
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_limaribu_2022_2_uv)
            startActivity(intent)
        }

        frame5K2016.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 5.000")
            intent.putExtra("TAHUN_EMISI", "TE 2016")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_limaribu)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_limaribu_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_limaribu_uv) // Tambahkan gambar UV untuk 2016
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_limaribu_2_uv) // Tambahkan gambar UV untuk 2016
            startActivity(intent)
        }

        frame2K2022.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 2.000")
            intent.putExtra("TAHUN_EMISI", "TE 2022")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_duaribu_2022)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_duaribu_2022_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_duaribu_2022_uv)
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_duaribu_2022_2_uv)
            startActivity(intent)
        }

        frame2K2016.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 2.000")
            intent.putExtra("TAHUN_EMISI", "TE 2016")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_duaribu)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_duaribu_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_duaribu_uv) // Tambahkan gambar UV untuk 2016
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_duaribu_2_uv) // Tambahkan gambar UV untuk 2016
            startActivity(intent)
        }

        frame1K2022.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 1.000")
            intent.putExtra("TAHUN_EMISI", "TE 2022")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_seribu_2022)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_seribu_2022_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_seribu_2022_uv)
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_seribu_2022_2_uv)
            startActivity(intent)
        }

        frame1K2016.setOnClickListener {
            val intent = Intent(this, DetailCiriActivity::class.java)
            intent.putExtra("NOMINAL", "Rp 1.000")
            intent.putExtra("TAHUN_EMISI", "TE 2016")
            intent.putExtra("GAMBAR_DEPAN", R.drawable.duit_seribu)
            intent.putExtra("GAMBAR_BELAKANG", R.drawable.duit_seribu_2)
            intent.putExtra("GAMBAR_UV_DEPAN", R.drawable.duit_seribu_uv) // Tambahkan gambar UV untuk 2016
            intent.putExtra("GAMBAR_UV_BELAKANG", R.drawable.duit_seribu_2_uv) // Tambahkan gambar UV untuk 2016
            startActivity(intent)
        }


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


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}