package com.example.verirupiah.ui.activity

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.verirupiah.ListJenisUang
import com.example.verirupiah.ui.adapter.ListJenisUangAdapter
import com.example.verirupiah.R
import com.example.verirupiah.databinding.ActivityJenisUangBinding

class JenisUangActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityJenisUangBinding
    private lateinit var listJenisUangAdapter: ListJenisUangAdapter
    private lateinit var listJenisUang: ListJenisUang
    var dataArrayList = ArrayList<ListJenisUang?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJenisUangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the action bar
        setSupportActionBar(binding.toolbar)

        // Hide default title text
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set custom title
        binding.toolbarTitle.text = "Nominal Uang T.E 2022"

        // Mengatur warna ikon secara dinamis
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        val nameList = arrayOf("Rp.100.000", "Rp.50.000", "Rp.20.000", "Rp.10.000", "Rp.5000", "Rp.2000", "Rp.1000")
        val tahun_emisiList = arrayOf("T.E 2022", "T.E 2022", "T.E 2022", "T.E 2022", "T.E 2022", "T.E 2022", "T.E 2022")

        val deskripsi_jenisuangList = intArrayOf(
            R.string.desc_seratus22,
            R.string.desc_limapuluh22,
            R.string.desc_duapuluh22,
            R.string.desc_sepuluh22,
            R.string.desc_limaribu22,
            R.string.desc_duaribu22,
            R.string.desc_seribu22
        )
        val gambarList = intArrayOf(
            R.drawable.duit_seratus_2022,
            R.drawable.duit_limapuluh_2022,
            R.drawable.duit_duapuluh_2022,
            R.drawable.duit_sepuluh_2022,
            R.drawable.duit_limaribu_2022,
            R.drawable.duit_duaribu_2022,
            R.drawable.duit_seribu_2022,
        )

        val belakangllist = intArrayOf(
            R.drawable.duit_seratus_2022_2,
            R.drawable.duit_limapuluh_2022_2,
            R.drawable.duit_duapuluh_2022_2,
            R.drawable.duit_sepuluh_2022_2,
            R.drawable.duit_limaribu_2022_2,
            R.drawable.duit_duaribu_2022_2,
            R.drawable.duit_seribu_2022_2,
        )

        for (i in gambarList.indices) {
            listJenisUang = ListJenisUang(nameList[i], tahun_emisiList[i], deskripsi_jenisuangList[i], gambarList[i], belakangllist[i])
            dataArrayList.add(listJenisUang)
        }

        listJenisUangAdapter = ListJenisUangAdapter(this@JenisUangActivity2, dataArrayList)
        binding.listviewjenisuang.adapter = listJenisUangAdapter
        binding.listviewjenisuang.isClickable = true

        binding.listviewjenisuang.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this@JenisUangActivity2, DetailJenisUang::class.java)
            intent.putExtra("nama_uang", nameList[i])
            intent.putExtra("tahun_emisi", tahun_emisiList[i])
            intent.putExtra("deskripsi_jenisuang", deskripsi_jenisuangList[i])
            intent.putExtra("gambar", gambarList[i])
            intent.putExtra("gambar_belakang", belakangllist[i])
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
