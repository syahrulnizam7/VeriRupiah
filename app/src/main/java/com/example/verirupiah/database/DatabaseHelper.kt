package com.example.verirupiah.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val TABLE_NAME = "tabel_gambar"
        internal const val COLUMN_ID = "ID"
        internal const val COLUMN_IMAGE = "IMAGE"
        internal const val COLUMN_LABEL = "LABEL"
        internal const val COLUMN_CONFIDENCE = "CONFIDENCE"
        internal const val COLUMN_TIMESTAMP = "TIMESTAMP"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_IMAGE BLOB, $COLUMN_LABEL TEXT, $COLUMN_CONFIDENCE REAL, $COLUMN_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP)"
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


    fun insertDetectionResult(imageData: ByteArray, label: String, confidence: Float) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_IMAGE, imageData)
        contentValues.put(COLUMN_LABEL, label)
        contentValues.put(COLUMN_CONFIDENCE, confidence)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }


    fun getLatestDetectionResult(): Cursor? {
        val db = this.readableDatabase
        return db.query(
            TABLE_NAME, null, null, null, null, null,
            "$COLUMN_TIMESTAMP DESC", "1"
        )
    }

    fun getAllDetectionResults(): Cursor? {
        val db = this.readableDatabase
        return db.query(TABLE_NAME, null, null, null, null, null, null)
    }
    fun deleteData(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }


}



