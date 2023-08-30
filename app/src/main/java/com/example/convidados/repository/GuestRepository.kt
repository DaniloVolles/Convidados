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

    val TABLE_NAME = DataBaseConstants.GUEST.TABLE_NAME
    val ID = DataBaseConstants.GUEST.COLUMNS.ID
    val NAME = DataBaseConstants.GUEST.COLUMNS.NAME
    val PRESENCE = DataBaseConstants.GUEST.COLUMNS.PRESENCE

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
            values.put(NAME, guest.name)
            values.put(PRESENCE, presence)

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
            values.put(PRESENCE, presence)
            values.put(NAME, guest.name)

            val selection = ID + "id = ?"
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
            val selection = ID + "id = ?"
            val args = arrayOf(id.toString())

            db.delete(TABLE_NAME, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("Range")
    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null

        try {

            val db = guestDataBase.readableDatabase

            val projection = arrayOf(ID, NAME, PRESENCE)

            val selection = ID + "id = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(PRESENCE))

                    guest = GuestModel(id, name, presence == 1)
                }
            }
            cursor.close()

        } catch (e: Exception) {
            return guest
        }
        return guest

    }


    @SuppressLint("Range")
    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {

            val db = guestDataBase.readableDatabase

            val projection = arrayOf(ID, NAME, PRESENCE)
            val cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(ID))
                    val name = cursor.getString(cursor.getColumnIndex(NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list

    }

    @SuppressLint("Range")
    fun getWithFilter(filter: Int): List<GuestModel> {
        val list = mutableListOf<GuestModel>()

        try {

            val db = guestDataBase.readableDatabase

            val cursor = db.rawQuery(
                "SELECT id, name, presence FROM guestdb WHERE presence = $filter",
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(ID))
                    val name = cursor.getString(cursor.getColumnIndex(NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(PRESENCE))

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