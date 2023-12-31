package com.example.convidados.repository

// Função desse código é conectar com o banco
// Ele fonrnece uma conexão com o banco

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.convidados.constants.DataBaseConstants

class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val NAME = "guestdb"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE " + DataBaseConstants.GUEST.TABLE_NAME + " (" +
                    DataBaseConstants.GUEST.COLUMNS.ID + " integer PRIMARY KEY AUTOINCREMENT," +
                    DataBaseConstants.GUEST.COLUMNS.NAME + " text," +
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE+ " integer);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("VERSION 1 OF THE DB")
    }

}