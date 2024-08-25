package com.example.verirupiah.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.verirupiah.OnboardingItem
import com.example.verirupiah.ui.adapter.OnboardingItemsAdapter
import com.example.verirupiah.R
import com.google.android.material.button.MaterialButton

class OnBoardingActivity : AppCompatActivity() {

    // Adapter untuk menampilkan item onboarding
    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    // Kontainer untuk indikator halaman onboarding
    private lateinit var indicatorsContainer: LinearLayout

    // Fungsi ini dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengaktifkan mode edge-to-edge
        enableEdgeToEdge()
        // Mengatur konten tampilan dari layout XML
        setContentView(R.layout.activity_onboarding)
        // Menambahkan padding agar konten tidak tumpang tindih dengan sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.onboarding)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Mengatur item-item untuk onboarding
        setOnboardingItems()
        // Mengatur indikator halaman
        setupIndicators()
        // Mengatur indikator aktif pada halaman pertama
        setCurrentIndicator(0)
    }

    // Fungsi untuk mengatur item-item onboarding
    private fun setOnboardingItems(){
        // Inisialisasi adapter dengan daftar item onboarding
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.pure,
                    title = "Keaslian Terjamin",
                    description = "memberikan solusi yang handal untuk mendeteksi keaslian uang rupiah dengan mudah dan akurat"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.trusted,
                    title = "Pendeteksi Terpercaya",
                    description = "Memberikan hasil yang akurat dalam memverifikasi apakah uang rupiah yang Anda miliki asli atau palsu"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.start,
                    title = "Mulai Sekarang",
                    description = "Ambil gambar uang rupiah, tunggu analisis otomatis, dan lihat hasilnya dalam sekejap!"
                )
            )
        )
        // Mengatur ViewPager2 dengan adapter
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemsAdapter
        // Menambahkan callback untuk perubahan halaman
        onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Mengubah indikator aktif sesuai halaman yang dipilih
                setCurrentIndicator(position)
            }
        })
        // Menonaktifkan over-scroll efek pada RecyclerView di ViewPager2
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        // Mengatur klik listener untuk tombol "Next"
        findViewById<ImageView>(R.id.imageNext).setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < onboardingItemsAdapter.itemCount) {
                // Pindah ke halaman berikutnya jika masih ada
                onboardingViewPager.currentItem += 1
            } else {
                // Navigasi ke aktivitas beranda jika sudah di halaman terakhir
                navigateToHomeActivity()
            }
        }

        // Mengatur klik listener untuk tombol "Get Started"
        findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener {
            // Navigasi ke aktivitas beranda
            navigateToHomeActivity()
        }
    }

    // Fungsi untuk navigasi ke aktivitas beranda
    private fun navigateToHomeActivity() {
        startActivity(Intent(applicationContext, NavbarActivity::class.java))
        finish()
    }

    // Fungsi untuk mengatur indikator halaman onboarding
    private fun setupIndicators() {
        // Menginisialisasi kontainer indikator
        indicatorsContainer = findViewById(R.id.indicatorContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                // Menambahkan indikator ke dalam kontainer
                indicatorsContainer.addView(it)
            }
        }
    }

    // Fungsi untuk mengatur indikator aktif
    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                // Mengubah drawable indikator menjadi aktif
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                // Mengubah drawable indikator menjadi tidak aktif
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}
