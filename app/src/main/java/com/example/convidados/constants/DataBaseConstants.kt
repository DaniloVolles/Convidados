package com.example.convidados.constants

import java.net.IDN
import java.util.jar.Attributes.Name

class DataBaseConstants private constructor() {

    object GUEST {

        const val ID = "guestid"
        const val TABLE_NAME = "Guest"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }

        object PRESENCE {
            const val FALSE = 0
            const val TRUE = 1
        }
    }

}