package itsallcode.org.fasttraceviewerforandroid.repository.typeconverters

import android.arch.persistence.room.TypeConverter
import openfasttrack.core.SpecificationItemId

/**
 * Created by thomasu on 2/3/18.
 */
class SpecificationItemIdConverter {

    @TypeConverter
    fun fromSpecificationItemId(value: SpecificationItemId?): String? {
        return if (value == null) null else value.toString()
    }

    @TypeConverter
    fun toSpecificationItemId(value: String?): SpecificationItemId? {
        return if (value == null) null else SpecificationItemId.parseId(value)
    }

}
