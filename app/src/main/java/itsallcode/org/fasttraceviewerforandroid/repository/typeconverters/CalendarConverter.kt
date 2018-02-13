package itsallcode.org.fasttraceviewerforandroid.repository.typeconverters

import android.arch.persistence.room.TypeConverter
import openfasttrack.core.SpecificationItemId
import java.util.*

/**
 * Created by thomasu on 2/3/18.
 */
class CalendarConverter {

    @TypeConverter
    fun fromCalendar(value: Calendar?): Long? {
        return if (value == null) null else value.timeInMillis
    }

    @TypeConverter
    fun toCalendar(value: Long?): Calendar? {
        return if (value == null) null else {
            val cal : Calendar = Calendar.getInstance()
            cal.timeInMillis = value
            return cal
        }
    }
}
