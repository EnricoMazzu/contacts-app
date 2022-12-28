package com.mzzlab.sample.contactsapp.data.impl

import android.database.Cursor

fun <T> Cursor?.map(block: (Cursor) -> T): List<T>? {
    return this?.let {
        val result = mutableListOf<T>()
        if(moveToFirst()){
            do {
                result.add(block(this))
            }while (moveToNext())
        }
        result
    }
}