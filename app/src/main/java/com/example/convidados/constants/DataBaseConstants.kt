package com.example.convidados.constants

import java.net.IDN
import java.util.jar.Attributes.Name

class DataBaseConstants private constructor() {

    object GUEST {
        const val TABLE_NAME = "guestdb"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }
    }

}