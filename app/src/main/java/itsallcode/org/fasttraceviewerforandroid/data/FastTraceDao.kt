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

package itsallcode.org.fasttraceviewerforandroid.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity

/**
 * Created by thomasu on 2/2/18.
 */
@Dao
interface FastTraceDao {

    @get:Query("SELECT * FROM FastTraceEntity")
    val all: LiveData<List<FastTraceEntity>>

    @Query("SELECT * FROM FastTraceEntity WHERE id=:fastTraceId")
    fun getSpecific(fastTraceId : Long) : FastTraceEntity

    /**
     * Inserts new FastDataEntity
     * @param entity Entities
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FastTraceEntity) : Long
}
