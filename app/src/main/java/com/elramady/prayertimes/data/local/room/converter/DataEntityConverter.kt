package com.elramady.prayertimes.data.local.room.converter

import androidx.room.TypeConverter
import com.elramady.prayertimes.data.local.room.DataEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataEntityConverter {

    @TypeConverter
    fun fromDataEntityList(dataEntities: List<DataEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<DataEntity>>() {}.type
        return gson.toJson(dataEntities, type)
    }

    @TypeConverter
    fun toDataEntityList(dataEntitiesString: String): List<DataEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<DataEntity>>() {}.type
        return gson.fromJson(dataEntitiesString, type)
    }
}
