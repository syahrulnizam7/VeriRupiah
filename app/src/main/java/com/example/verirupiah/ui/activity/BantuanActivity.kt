package com.example.verirupiah.ui.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.verirupiah.R
import com.example.verirupiah.databinding.ActivityBantuanBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BantuanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBantuanBinding
    private var isCardVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBantuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the action bar
        setSupportActionBar(binding.toolbar)

        // Hide default title text
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set custom title
        binding.toolbarTitle.text = "Bantuan"

        // Mengatur warna ikon secara dinamis
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        // Floating Action Button for Contact Info
        val fabContactInfo: FloatingActionButton = binding.fabContactInfo
        val contactInfoCard = binding.contactInfoCard

        fabContactInfo.setOnClickListener {
            if (isCardVisible) {
                // Hide the CardView with scale down and translate to FAB animation, change icon and foreground
                val scaleDownTranslateAnimation: Animation = AnimationUtils.loadAnimation(applicationContext,
                    R.anim.scale_down
                )
                contactInfoCard.startAnimation(scaleDownTranslateAnimation)
                contactInfoCard.visibility = View.GONE
                fabContactInfo.setImageResource(R.drawable.ic_contactsupp)
                fabContactInfo.foreground = getDrawable(R.drawable.fab_gradient2)
            } else {
                // Show the CardView with scale up and translate from FAB animation, change icon and foreground
                val scaleUpTranslateAnimation: Animation = AnimationUtils.loadAnimation(applicationContext,
                    R.anim.scale_up
                )
                contactInfoCard.visibility = View.VISIBLE
                contactInfoCard.startAnimation(scaleUpTranslateAnimation)
                fabContactInfo.setImageResource(R.drawable.ic_close)
                fabContactInfo.foreground = getDrawable(R.drawable.fab_gradient3)
            }
            isCardVisible = !isCardVisible
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
