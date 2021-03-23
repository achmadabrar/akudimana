package com.abrar.akudimana.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun mutableListToString(string: MutableList<LocationModel>): String {
            val type = object : TypeToken<MutableList<LocationModel>>() {}.type
            return Gson().toJson(string, type)
        }

        @TypeConverter
        @JvmStatic
        fun stringToMutableList(string: String): MutableList<LocationModel> {
            val type = object : TypeToken<MutableList<LocationModel>>() {}.type
            return Gson().fromJson(string, type)
        }
    }
}