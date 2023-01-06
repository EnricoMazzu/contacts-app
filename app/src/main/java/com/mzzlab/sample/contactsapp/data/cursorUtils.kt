package com.mzzlab.sample.contactsapp.data

import android.database.Cursor
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull

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

fun Cursor.optStringByName(columnName: String): String? {
    val index = getColumnIndex(columnName)
    return getStringOrNull(index)
}

fun Cursor.optIntByName(columnName: String, defValue: Int = -1): Int {
    val index = getColumnIndex(columnName)
    return getIntOrNull(index) ?: defValue
}

fun Cursor.getStringByName(columnName: String): String {
    return getString(getColumnIndexOrThrow(columnName))
}

fun Cursor.getIntByName(columnName: String): Int {
    return getInt(getColumnIndexOrThrow(columnName))
}