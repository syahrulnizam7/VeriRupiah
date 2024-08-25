package com.example.verirupiah.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.verirupiah.R
import com.example.verirupiah.ui.fragment.HomeFragment
import com.example.verirupiah.ui.fragment.InfoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class NavbarActivity : AppCompatActivity() {
    private lateinit var navview: BottomNavigationView
    private lateinit var photoURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navbar)

        // Mengaktifkan mode edge-to-edge


        navview = findViewById(R.id.nav_view)
        replace(HomeFragment())

        navview.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replace(HomeFragment())
                R.id.navigation_info -> replace(InfoFragment())
            }
            true
        }

        val floatingButton: FloatingActionButton = findViewById(R.id.floatingbutton)
        floatingButton.setOnClickListener {
            showCurrencySelectionDialog()
        }

    }

    //    Menampilkan fragment baru sesuai dengan yang dipilih pada BottomNavigationView
    private fun replace(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navhost, fragment)
        fragmentTransaction.commit()
    }

    // Menampilkan dialog untuk memilih jenis uang
    private fun showCurrencySelectionDialog() {
        // Inflate layout untuk dialog pemilihan jenis uang
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_currency_selection, null)

        // Membuat AlertDialog
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogView)

        // Mengaktifkan animasi sebelum menampilkan dialog
        val dialogInAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.dialog_in)
        dialogView.startAnimation(dialogInAnimation)

        val alertDialog = alertDialogBuilder.create()


        dialogView.findViewById<Button>(R.id.button_oke).setOnClickListener {
            alertDialog.dismiss()
            showBottomSheetDialog()
        }

        // Menampilkan dialog
        alertDialog.show()

        // Mengatur layout dan gravity agar dialog berada di tengah layar
        val window = alertDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val layoutParams = window?.attributes
        layoutParams?.gravity = Gravity.CENTER
        window?.attributes = layoutParams
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

    // Memilih gambar dari galeri
    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Mengambil gambar dari kamera
    private fun takePictureFromCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            // Lanjutkan dengan mengambil gambar dari kamera
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoFile = createImageFile()
            photoURI = FileProvider.getUriForFile(this, "${packageName}.fileprovider", photoFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(intent, TAKE_PICTURE_REQUEST)
        }
    }

    // Metode untuk membuat file gambar
    private fun createImageFile(): File {
        // Buat nama file berdasarkan timestamp
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, ambil gambar
                takePictureFromCamera()
            } else {
                // Tampilkan pesan bahwa izin diperlukan
                showToast("Izin kamera diperlukan untuk mengambil gambar.")
            }
        }
    }



    // Menangani hasil dari pemilihan gambar dari galeri atau pengambilan gambar dari kamera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val selectedImageUri = data?.data
                    selectedImageUri?.let { uri ->
                        // Mulai aktivitas crop
                        CropImage.activity(uri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(this)
                    }
                }

                TAKE_PICTURE_REQUEST -> {
                    // Tidak perlu menangani data karena kita menggunakan EXTRA_OUTPUT
                    // Ambil URI yang sudah dibuat
                    // Lanjutkan ke aktivitas crop
                    CropImage.activity(photoURI) // Pastikan photoURI terdefinisi dan valid
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this)
                }

                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    // Hasil dari aktivitas crop
                    val result = CropImage.getActivityResult(data)
                    val croppedImageUri = result.uri

                    // Lanjutkan ke aktivitas DeteksiActivity dengan URI gambar yang di-crop
                    val intent = Intent(this, DeteksiActivity::class.java)
                    intent.putExtra("imageUri", croppedImageUri.toString())
                    startActivity(intent)
                }
            }
        }
    }


    // Mengambil URI dari gambar bitmap
    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }


    // Menampilkan Toast dengan pesan yang diberikan
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val TAKE_PICTURE_REQUEST = 2
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}
