package com.example.convidados.repository

// A função do repository é manipular os dados utilizando a conexão com o banco

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import androidx.core.content.contentValuesOf
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel
import kotlinx.coroutines.selects.select

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    private val TABLE_NAME = DataBaseConstants.GUEST.TABLE_NAME
    private val COLUMN_ID = DataBaseConstants.GUEST.COLUMNS.ID
    private val COLUMN_NAME = DataBaseConstants.GUEST.COLUMNS.NAME
    private val COLUMN_PRESENCE = DataBaseConstants.GUEST.COLUMNS.PRESENCE

    // Singleton
    companion object {
        private lateinit var repository: GuestRepository
        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {

        return try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0
            val values = ContentValues()

            values.put(COLUMN_NAME, guest.name)
            values.put(COLUMN_PRESENCE, presence)

            db.insert(TABLE_NAME, null, values)

            true
        } catch (e: Exception) {
            false
        }

    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(COLUMN_PRESENCE, presence)
            values.put(COLUMN_NAME, guest.name)

            val selection = COLUMN_ID + "id = ?"
            val args = arrayOf(guest.id.toString())

            db.update(TABLE_NAME, values, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = guestDataBase.writableDatabase
            val selection = COLUMN_ID + "id = ?"
            val args = arrayOf(id.toString())

            db.delete(TABLE_NAME, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("Range")
    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {

            val db = guestDataBase.readableDatabase

            val selection = arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_PRESENCE)
            val cursor = db.query(
                TABLE_NAME,
                selection,
                null,
                null,
                null,
                null,
                null
            )
            if (cursor != null && cursor.count > 0){
                while (cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                    val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(COLUMN_PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list

    }
}