package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import itsallcode.org.fasttraceviewerforandroid.data.*
import itsallcode.org.fasttraceviewerforandroid.repository.entities.*
import itsallcode.org.fasttraceviewerforandroid.repository.typeconverters.CalendarConverter
import itsallcode.org.fasttraceviewerforandroid.repository.typeconverters.PathConverter

/**
 * Created by thomasu on 2/2/18.
 */

@Database(entities = [FastTraceEntity::class],
            version = FastTraceDatabase.DATABASE_VERSION,
            exportSchema = false)
@TypeConverters(CalendarConverter::class, PathConverter::class)
abstract class FastTraceDatabase : RoomDatabase() {

    /**
     * Gets the FastTrace Dao used to access all available workspace.
     *
     * @return FastTrace Dao used on the database.
     */
    abstract fun fastTraceDao(): FastTraceDao

    companion object {

        /**
         * Database name.
         */
        const val DATABASE_NAME = "fast_trace_database.db"

        /**
         * Database version.
         */
        const val DATABASE_VERSION = 1
    }
}
