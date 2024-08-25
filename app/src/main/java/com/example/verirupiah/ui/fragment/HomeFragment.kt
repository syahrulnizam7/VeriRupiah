package com.example.verirupiah.ui.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.verirupiah.ui.activity.BantuanActivity
import com.example.verirupiah.ui.activity.BeritaActivity
import com.example.verirupiah.ui.activity.CiriActivity
import com.example.verirupiah.database.DatabaseHelper
import com.example.verirupiah.ui.activity.JenisUangActivity
import com.example.verirupiah.R
import com.example.verirupiah.ui.activity.RiwayatDeteksiActivity
import com.example.verirupiah.ui.activity.SejarahActivity
import com.example.verirupiah.databinding.FragmentHomeBinding
import com.example.verirupiah.ui.activity.CiriCiriActivity
import com.example.verirupiah.ui.activity.JenisUangActivity2

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize DatabaseHelper
        databaseHelper = DatabaseHelper(requireContext())

        // Refresh data and view
        refreshDataAndView()

        val imageView1 = binding.imageFitur1
        val imageView2 = binding.imageFitur2
        val imageView3 = binding.imageFitur3
        val imageView4 = binding.imageFitur4
        val imageView5 = binding.imageFitur5
        val TextViewRiwayat = binding.historyTextView

        imageView1.setOnClickListener {
            // Inflate the dialog layout
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_choose_nominal, null)

            // Create the AlertDialog
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()

            // Find buttons and set click listeners
            val btnNominal2016 = dialogView.findViewById<Button>(R.id.btn_nominal_2016)
            val btnNominal2022 = dialogView.findViewById<Button>(R.id.btn_nominal_2022)

            btnNominal2016.setOnClickListener {
                // Handle click for T.E 2016
                val intent = Intent(requireContext(), JenisUangActivity::class.java)
                intent.putExtra("nominal_year", "2016")
                startActivity(intent)
                dialog.dismiss()
            }

            btnNominal2022.setOnClickListener {
                // Handle click for T.E 2022
                val intent = Intent(requireContext(), JenisUangActivity2::class.java)
                intent.putExtra("nominal_year", "2022")
                startActivity(intent)
                dialog.dismiss()
            }

            // Show the dialog
            dialog.show()
        }
//
//        imageView2.setOnClickListener {
//            val dialog = CiriActivity(requireContext(), "ciri_uang.pdf")
//            dialog.show()
//        }
        imageView2.setOnClickListener {
            val intent = Intent(requireContext(), CiriCiriActivity::class.java)
            startActivity(intent)
        }

        imageView3.setOnClickListener {
            val intent = Intent(requireContext(), SejarahActivity::class.java)
            startActivity(intent)
        }

        imageView4.setOnClickListener {
            val intent = Intent(requireContext(), BantuanActivity::class.java)
            startActivity(intent)
        }

        imageView5.setOnClickListener {
            val intent = Intent(requireContext(), BeritaActivity::class.java)
            startActivity(intent)
        }

        TextViewRiwayat.setOnClickListener {
            val intent = Intent(requireContext(), RiwayatDeteksiActivity::class.java)
            startActivity(intent)
        }

        // Referensi ke TextView
        val sloganTextView = view.findViewById<TextView>(R.id.sloganTextView)

        // Buat animasi alpha
        val alphaAnimation = AlphaAnimation(0.0f, 1.0f).apply {
            duration = 2000 // durasi animasi dalam milidetik
            startOffset = 500 // penundaan sebelum animasi dimulai
            fillAfter = true // membuat perubahan tetap setelah animasi selesai
        }

        // Jalankan animasi
        sloganTextView.startAnimation(alphaAnimation)
    }

    override fun onResume() {
        super.onResume()
        // Refresh data and view each time the fragment is resumed
        refreshDataAndView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshDataAndView() {
        // Get the latest detection result from the database
        val cursor = databaseHelper.getLatestDetectionResult()
        if (cursor != null && cursor.moveToFirst()) {
            // Get image bytes from the database
            val imageBytes = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE))
            // Convert bytes to bitmap
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            // Set the bitmap to the ImageView
            binding.imageFromDatabase.setImageBitmap(bitmap)
            // Set scaleType to centerCrop to ensure the image fits the container
            binding.imageFromDatabase.scaleType = ImageView.ScaleType.CENTER_CROP
            // Hide the "No image yet" text
            binding.noImageText.visibility = View.GONE

            // Get label and confidence from the database
            val labelIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LABEL)
            val confidenceIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CONFIDENCE)
            val label = cursor.getString(labelIndex)
            val confidence = cursor.getFloat(confidenceIndex)
            // Check if label and confidence are not null
            if (label != null && confidence != null) {
                // Transform label into desired format
                val formattedLabel = when (label) {
                    "uang100asliTE16" -> "Rp.100.000 Asli T.E 2016"
                    "uang100asliTE22" -> "Rp.100.000 Asli T.E 2022"
                    "uang100palsuTE16" -> "Rp.100.000 Palsu T.E 2016"
                    "uang100palsuTE22" -> "Rp.100.000 Palsu T.E 2022"
                    "uang50asliTE16" -> "Rp.50.000 Asli T.E 2016"
                    "uang50asliTE22" -> "Rp.50.000 Asli T.E 2022"
                    "uang50palsuTE16" -> "Rp.50.000 Palsu T.E 2016"
                    "uang50palsuTE22" -> "Rp.50.000 Palsu T.E 2022"
                    "unknown" -> "Tidak dapat mendeteksi"
                    else -> "Tidak ada hasil deteksi"
                }
                // Set label and confidence to the TextView in the desired format
                binding.detectionResultTextView.text = "$formattedLabel\nPrediksi: ${confidence.toInt()}%"
            }
        } else {
            // If the database is empty, display "No detection result"
            binding.imageFromDatabase.setImageResource(R.drawable.ic_noimage)
            binding.noImageText.visibility = View.VISIBLE
            binding.detectionResultTextView.text = "Tidak ada hasil deteksi"
        }
        // Close the cursor after use
        cursor?.close()
    }

}
