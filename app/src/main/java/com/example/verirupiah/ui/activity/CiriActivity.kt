package com.example.verirupiah.ui.activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import com.example.verirupiah.R
import com.github.barteksc.pdfviewer.PDFView

class CiriActivity(context: Context, private val pdfAssetName: String) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_ciri)

        val pdfView: PDFView = findViewById(R.id.pdfView)
        pdfView.fromAsset(pdfAssetName).load()

        val closeButton: ImageButton = findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            dismiss()
        }
    }
}
