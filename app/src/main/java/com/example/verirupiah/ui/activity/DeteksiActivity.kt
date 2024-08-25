package com.example.verirupiah.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.verirupiah.R
import com.example.verirupiah.database.DatabaseHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.tensorflow.lite.Interpreter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class DeteksiActivity : AppCompatActivity() {
    private lateinit var photoURI: Uri

    private lateinit var imgBtnReupload: ImageButton
    private lateinit var imgClose: ImageButton
    private lateinit var imgUploaded: ImageView
    private lateinit var backButton: ImageButton
    private lateinit var btnDeteksi: MaterialButton
    private lateinit var interpreter: Interpreter

    // Tambahkan daftar label
    private val labels = arrayOf(
        "uang100asliTE16",
        "uang100asliTE22",
        "uang100palsuTE16",
        "uang100palsuTE22",
        "uang50asliTE16",
        "uang50asliTE22",
        "uang50palsuTE16",
        "uang50palsuTE22",
        "unknown"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        // Load model TFLite saat activity dibuat
        interpreter = Interpreter(loadModelFile("modelVeriRupiahVersi2.tflite"))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deteksi)

        // Tombol kembali
        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            navigateToHomeActivity()
        }

        // Inisialisasi view
        imgUploaded = findViewById(R.id.imgUploaded)
        imgBtnReupload = findViewById(R.id.imgBtnReupload)
        imgClose = findViewById(R.id.imgClose)
        btnDeteksi = findViewById(R.id.btnDeteksi)

        // Sembunyikan image button "Reupload" secara default
        imgBtnReupload.visibility = View.INVISIBLE

        // Menerima gambar dari NavbarActivity
        val imageUriString = intent.getStringExtra("imageUri")
        val imageBitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")

        // Tampilkan gambar di ImageView
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            imgUploaded.setImageURI(imageUri)
            imgUploaded.setPadding(0, 0, 0, 0)  // Hapus padding jika ada gambar
            imgClose.visibility = View.VISIBLE

        } else if (imageBitmap != null) {
            imgUploaded.setImageBitmap(imageBitmap)
            imgUploaded.setPadding(0, 0, 0, 0)  // Hapus padding jika ada gambar
            imgClose.visibility = View.VISIBLE

        }


        // Tambahkan onClickListener untuk tombol "Close"
        imgClose.setOnClickListener {
            // Set gambar "missing" dengan background hijau muda dan gambar hijau tua
            imgUploaded.setImageResource(R.drawable.ic_missingimg)
            imgUploaded.setBackgroundResource(R.drawable.dark_transparent_background)

            // Tambahkan padding pada ImageView
            val paddingDp = resources.getDimensionPixelSize(R.dimen.padding_missing_image)
            imgUploaded.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)

            // Tampilkan image button "Reupload"
            imgBtnReupload.visibility = View.VISIBLE
            imgClose.visibility = View.GONE
            showToast("Gambar berhasil dihapus")
        }

        // Tambahkan onClickListener untuk tombol "Reupload"
        imgBtnReupload.setOnClickListener {
            // Tampilkan dialog bottom sheet saat tombol "Reupload" ditekan
            showBottomSheetDialog()
        }

        // Tambahkan onClickListener untuk tombol "Deteksi"
        btnDeteksi.setOnClickListener {
            // Lakukan deteksi
            deteksiUang()
        }
    }

    // Menampilkan bottom sheet dialog untuk memilih antara mengambil gambar dari kamera atau galeri
    private fun showBottomSheetDialog() {
        // Inflate layout untuk bottom sheet dialog
        val dialogView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, null)

        // Membuat BottomSheetDialog
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(dialogView)

        // Menangani aksi saat tombol cameraImageButton ditekan
        dialogView.findViewById<ImageButton>(R.id.cameraImageButton).setOnClickListener {
            takePictureFromCamera()
            bottomSheetDialog.dismiss()
        }

        // Menangani aksi saat tombol galleryImageButton ditekan
        dialogView.findViewById<ImageButton>(R.id.galleryImageButton).setOnClickListener {
            chooseImageFromGallery()
            bottomSheetDialog.dismiss()
        }

        // Menutup dialog saat tombol Cancel ditekan
        dialogView.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        // Menampilkan bottom sheet dialog
        bottomSheetDialog.show()
    }

    // Mengambil gambar dari kamera
    private fun takePictureFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            // Create a file to save the image
            val photoFile: File? = createImageFile() // Use a method to create the file
            val photoURI: Uri = FileProvider.getUriForFile(
                this@DeteksiActivity,
                "${packageName}.fileprovider",
                photoFile!!
            )
            putExtra(MediaStore.EXTRA_OUTPUT, photoURI) // Use EXTRA_OUTPUT to save image to file
        }
        startActivityForResult(intent, TAKE_PICTURE_REQUEST)
    }


    // Metode untuk membuat file gambar
    private fun createImageFile(): File {
        val timestamp = System.currentTimeMillis()
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("IMG_$timestamp", ".jpg", storageDir).apply {
            // Save the file path for later use
            photoURI = Uri.fromFile(this)
        }
    }


    // Memilih gambar dari galeri
    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Menangani hasil dari pemilihan gambar dari galeri atau pengambilan gambar dari kamera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val selectedImageUri = data?.data
                    startCrop(selectedImageUri)
                }

                TAKE_PICTURE_REQUEST -> {
                    // Use the saved photo URI directly for cropping
                    startCrop(photoURI)
                }

                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    if (resultCode == Activity.RESULT_OK) {
                        val resultUri = result.uri
                        imgUploaded.setImageURI(resultUri)
                        imgUploaded.setPadding(0, 0, 0, 0)
                        imgClose.visibility = View.VISIBLE
                        imgBtnReupload.visibility = View.INVISIBLE
                        showToast("Gambar berhasil diubah")
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        val error = result.error
                        showToast("Crop gagal: ${error.message}")
                    }
                }
            }
        }
    }


    // Memulai aktivitas cropping dengan rasio 1:1
    private fun startCrop(imageUri: Uri?) {
        CropImage.activity(imageUri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1)
            .start(this)
    }

    // Mengkonversi Bitmap ke Uri
    private fun getImageUri(bitmap: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    // Menampilkan Toast dengan pesan yang diberikan
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun loadModelFile(modelPath: String): MappedByteBuffer {
        val fileDescriptor = assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun deteksiUang() {
        val bitmap = (imgUploaded.drawable as BitmapDrawable).bitmap
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val inputBuffer = convertBitmapToByteBuffer(resizedBitmap)


        val result = Array(1) { FloatArray(9) }
        interpreter.run(inputBuffer, result)

        // Temukan indeks dengan nilai tertinggi
        val maxIdx = result[0].indices.maxByOrNull { result[0][it] } ?: -1
        val label = labels[maxIdx]
        val confidence = result[0][maxIdx] * 100  // Konversi ke persentase

        // Simpan gambar ke database
        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageInBytes = byteArrayOutputStream.toByteArray()
        val databaseHelper = DatabaseHelper(this)

        databaseHelper.insertDetectionResult(imageInBytes, label, confidence)

        // Jangan lupa untuk menutup database setelah digunakan
        databaseHelper.close()

        showBottomSheetResult(label, confidence)
    }


    private fun showBottomSheetResult(label: String, confidence: Float) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_hasil_prediksi, null)

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(dialogView)

        val tvResult = dialogView.findViewById<TextView>(R.id.tvResult)
        val tvConfidence = dialogView.findViewById<TextView>(R.id.tvConfidence)
        val tvDescription = dialogView.findViewById<TextView>(R.id.tvDescription)
        val btnClose = dialogView.findViewById<Button>(R.id.btnClose)

        when (label) {
            "uang100asliTE16" -> {
                tvResult.text = "Uang 100 Ribu Asli T.E 2016"
                tvConfidence.text = "${"%.2f".format(confidence)}%"
                tvDescription.text = getString(R.string.desc_seratus)
            }
            "uang100asliTE22" -> {
                tvResult.text = "Uang 100 Ribu Asli T.E 2022"
                tvConfidence.text = "${"%.2f".format(confidence)}%"
                tvDescription.text = getString(R.string.desc_seratus22)
            }
            "uang100palsuTE16" -> {
                tvResult.text = "Uang 100 Ribu Palsu T.E 2016"
                tvConfidence.text = " ${"%.2f".format(confidence)}%"
                tvDescription.text = getString(R.string.desc_uangpalsu)
            }
            "uang100palsuTE22" -> {
                tvResult.text = "Uang 100 Ribu Palsu T.E 2022"
                tvConfidence.text = "${"%.2f".format(confidence)}%"
                tvDescription.text = getString(R.string.desc_uangpalsu)
            }
            "uang50asliTE16" -> {
                tvResult.text = "Uang 50 Ribu Asli T.E 2016"
                tvConfidence.text = "${"%.2f".format(confidence)}%"
                tvDescription.text = getString(R.string.desc_limapuluh)
            }
            "uang50asliTE22" -> {
                tvResult.text = "Uang 50 Ribu Asli T.E 2022"
                tvConfidence.text = "${"%.2f".format(confidence)}%"
                tvDescription.text = getString(R.string.desc_limapuluh22)
            }

            "uang50palsuTE16" -> {
                tvResult.text = "Uang 50 Ribu Palsu T.E 2016"
                tvConfidence.text = "${"%.2f".format(confidence)}%"
                tvDescription.text = getString(R.string.desc_uangpalsu)
            }
            "uang50palsuTE22" -> {
                tvResult.text = "Uang 50 Ribu Palsu T.E 2022"
                tvConfidence.text = "${"%.2f".format(confidence)}%"
                tvDescription.text = getString(R.string.desc_uangpalsu)
            }

            "unknown" -> {
                tvResult.text = "Hasil Tidak Diketahui"
                tvConfidence.text = "${"%.2f".format(confidence)}%"
                tvDescription.text = getString(R.string.desc_unknown)
            }

            else -> {
                tvResult.text = "Hasil Tidak Diketahui"
                tvConfidence.text = "Deskripsi untuk hasil yang tidak diketahui."
                tvDescription.text = getString(R.string.desc_unknown)
            }
        }

        btnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }


    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(224 * 224)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var pixel = 0
        for (i in 0 until 224) {
            for (j in 0 until 224) {
                val value = intValues[pixel++]

                byteBuffer.putFloat(((value shr 16) and 0xFF) / 255f)
                byteBuffer.putFloat(((value shr 8) and 0xFF) / 255f)
                byteBuffer.putFloat((value and 0xFF) / 255f)
            }
        }
        return byteBuffer
    }

    private fun navigateToHomeActivity() {
        startActivity(Intent(this, NavbarActivity::class.java))
        finish()
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val TAKE_PICTURE_REQUEST = 2
    }
}
