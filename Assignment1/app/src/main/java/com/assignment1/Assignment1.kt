package com.assignment1

import android.app.Application
import com.assignment1.data.database.UserDatabase

class Assignment1: Application() {
    val database by lazy { UserDatabase.getInstance(this) }
}