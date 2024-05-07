package my.destinyStudio.firetimer.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import my.destinyStudio.firetimer.data.IntervalsInfo
import java.util.*

class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID? {
        return UUID.fromString(string)
    }
}
class ListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<IntervalsInfo> {
        val listType = object : TypeToken<List<IntervalsInfo>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<IntervalsInfo>): String {
        return gson.toJson(list)
    }
}