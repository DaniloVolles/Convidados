package com.example.convidados.repository

import android.content.Context
import com.example.convidados.GuestModel

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

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

    fun insert() {

    }

    fun save() {
        TODO("NOT YET IMPLEMENTED")
    }
}