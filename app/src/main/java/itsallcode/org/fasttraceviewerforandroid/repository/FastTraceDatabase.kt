/*-
 * #%L
 \* FastTraceViewerForAndroid
 * %%
 * Copyright (C) 2017 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
