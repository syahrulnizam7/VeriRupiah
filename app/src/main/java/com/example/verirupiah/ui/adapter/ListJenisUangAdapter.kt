package com.example.verirupiah.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.verirupiah.R
import com.example.verirupiah.ListJenisUang

class ListJenisUangAdapter(context: Context, dataArrayList: ArrayList<ListJenisUang?>?) :
    ArrayAdapter<ListJenisUang>(context, R.layout.list_item_jenisrupiah, dataArrayList!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val listJenisUang = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_jenisrupiah, parent, false)
        }
        val listGambar = view!!.findViewById<ImageView>(R.id.list_gambar)
        val listName = view.findViewById<TextView>(R.id.list_name)
        val listTE = view.findViewById<TextView>(R.id.list_te)

        listGambar.setImageResource(listJenisUang!!.gambar)
        listName.text = listJenisUang.nama_uang
        listTE.text = listJenisUang.tahun_emisi

        return view
    }
}
