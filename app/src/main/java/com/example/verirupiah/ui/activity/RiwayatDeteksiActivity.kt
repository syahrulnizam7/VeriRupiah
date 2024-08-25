package com.example.verirupiah.ui.activity

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.verirupiah.R
import com.example.verirupiah.ui.adapter.RiwayatDeteksiAdapter
import com.example.verirupiah.database.DatabaseHelper
import com.example.verirupiah.databinding.ActivityRiwayatDeteksiBinding

class RiwayatDeteksiActivity : AppCompatActivity(),
    RiwayatDeteksiAdapter.OnDeleteItemClickListener {
    private lateinit var binding: ActivityRiwayatDeteksiBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RiwayatDeteksiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatDeteksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the action bar
        setSupportActionBar(binding.toolbar)

        // Hide default title text
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set custom title
        binding.toolbarTitle.text = "Riwayat Deteksi"

        // Mengatur warna ikon secara dinamis
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        // Inisialisasi DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Perhatikan bahwa kita memberikan databaseHelper ke adapter
        adapter = RiwayatDeteksiAdapter(databaseHelper.getAllDetectionResults()!!, databaseHelper, this)
        recyclerView.adapter = adapter

        // Tampilkan total count dari tabel_gambar
        updateTotalCount()

    }

    override fun onDestroy() {
        super.onDestroy()
        // Tutup Cursor dan DatabaseHelper setelah digunakan
        databaseHelper.close()
        adapter.closeCursor()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDeleteItem() {
        // Panggil metode ini setelah penghapusan item
        updateTotalCount()
    }

    private fun updateTotalCount() {
        val totalCount = getTotalCountFromDatabase()
        binding.totalCount.text = "$totalCount uang telah dideteksi"
    }

    private fun getTotalCountFromDatabase(): Int {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM tabel_gambar", null)
        return if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            0
        }.also {
            cursor.close()
        }
    }
}
