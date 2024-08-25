package com.example.verirupiah.ui.adapter

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.verirupiah.R
import com.example.verirupiah.database.DatabaseHelper

class RiwayatDeteksiAdapter(
    private val cursor: Cursor,
    private val databaseHelper: DatabaseHelper,
    private val onDeleteItemClickListener: OnDeleteItemClickListener
) : RecyclerView.Adapter<RiwayatDeteksiAdapter.ViewHolder>() {

    interface OnDeleteItemClickListener {
        fun onDeleteItem()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Inisialisasi elemen UI
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val labelTextView: TextView = itemView.findViewById(R.id.labelTextView)
        val confidenceTextView: TextView = itemView.findViewById(R.id.confidenceTextView)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)

        init {
            // Set OnClickListener untuk deleteIcon
            deleteIcon.setOnClickListener {
                // Munculkan dialog konfirmasi saat pengguna mengklik ikon delete
                showDeleteConfirmationDialog()
            }
        }

        // Fungsi untuk menampilkan dialog konfirmasi hapus
        private fun showDeleteConfirmationDialog() {
            // Inflate layout dialog
            val inflater = itemView.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_delete_confirmation, null)

            // Buat AlertDialog
            val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                .setView(dialogView)
                .setCancelable(true)

            // Inisialisasi elemen UI dalam dialog
            val dialog = alertDialogBuilder.create()
            val buttonYes = dialogView.findViewById<Button>(R.id.buttonYes)
            val buttonNo = dialogView.findViewById<Button>(R.id.buttonNo)

            // Set OnClickListener untuk tombol "Ya"
            buttonYes.setOnClickListener {
                // Dapatkan posisi item yang diklik
                val position = adapterPosition
                // Memeriksa jika posisi valid
                if (position != RecyclerView.NO_POSITION) {
                    // Pindahkan cursor ke posisi yang sesuai
                    cursor.moveToPosition(position)
                    // Dapatkan ID dari data yang akan dihapus
                    val itemId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                    // Hapus data dari database menggunakan ID
                    deleteItem(itemId, position)
                }
                // Tutup dialog
                dialog.dismiss()
            }

            // Set OnClickListener untuk tombol "Tidak"
            buttonNo.setOnClickListener {
                // Tutup dialog
                dialog.dismiss()
            }

            // Tampilkan dialog
            dialog.show()
        }

        // Fungsi untuk menghapus item dari database
        private fun deleteItem(itemId: Long, position: Int) {
            // Panggil metode deleteData di DatabaseHelper
            databaseHelper.deleteData(itemId)
            // Panggil notifyDataSetChanged untuk memperbarui tampilan
            cursor.requery()
            notifyDataSetChanged()

            // Informasikan activity bahwa item telah dihapus
            onDeleteItemClickListener.onDeleteItem()
            // Tampilkan toast notifikasi
            Toast.makeText(itemView.context, "Data telah berhasil dihapus", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat_deteksi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!cursor.moveToPosition(position)) {
            return
        }

        val imageBytes = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE))

        val label = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LABEL))
        val confidence = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONFIDENCE))

        // Ubah byte array menjadi bitmap dan tampilkan di ImageView
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.imageView.setImageBitmap(bitmap)

        // Set labelTextView berdasarkan kondisi
        val labelDisplay = when (label) {
            "uang100asliTE16" -> "Rp.100.000 Asli T.E 2016"
            "uang100asliTE22" -> "Rp.100.000 Asli T.E 2022"
            "uang100palsuTE16" -> "Rp.100.000 Palsu T.E 2016"
            "uang100palsuTE22" -> "Rp.100.000 Palsu T.E 2022"
            "uang50asliTE16" -> "Rp.50.000 Asli T.E 2016"
            "uang50asliTE22" -> "Rp.50.000 Asli T.E 2022"
            "uang50palsuTE16" -> "Rp.50.000 Palsu T.E 2016"
            "uang50palsuTE22" -> "Rp.50.000 Palsu T.E 2022"
            "unknown" -> "Tidak dapat mendeteksi"
            else -> label // Default handling
        }
        holder.labelTextView.text = labelDisplay

        // Set color untuk labelTextView berdasarkan label
        val colorRes = when (label) {
            "uang100asliTE16","uang100asliTE22", "uang50asliTE16","uang50asliTE22" -> R.color.custom_color_primary // warna hijau
            "uang100palsuTE16","uang100palsuTE22", "uang50palsuTE16","uang50palsuTE22" -> R.color.red // warna merah
            else -> android.R.color.black // Default warna hitam
        }
        holder.labelTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, colorRes))

        holder.confidenceTextView.text = "Prediksi: ${confidence}%"
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    fun closeCursor() {
        cursor.close()
    }
}
