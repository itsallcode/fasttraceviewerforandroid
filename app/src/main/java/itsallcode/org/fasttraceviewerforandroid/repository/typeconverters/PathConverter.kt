package itsallcode.org.fasttraceviewerforandroid.repository.typeconverters

import android.arch.persistence.room.TypeConverter
import openfasttrack.core.SpecificationItemId
import java.io.File
import java.nio.file.Path
import java.util.*

/**
 * Created by thomasu on 2/3/18.
 */
class PathConverter {

    @TypeConverter
    fun fromPath(value: Path?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toPath(value: String?): Path? {
        return File(value).toPath()
    }
}
