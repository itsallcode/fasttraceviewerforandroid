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

import android.arch.lifecycle.LiveData

import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import openfasttrack.core.LinkedSpecificationItem
import java.nio.file.Path
import java.util.*

/**
 * Created by thomasu on 1/27/18.
 */

abstract class FastTraceRepository {
    private var cache = Pair<Long?, List<LinkedSpecificationItem>?>(null, null)

    abstract val allFastTraceItems: LiveData<List<FastTraceEntity>>
    abstract fun add(name: String, creationData: Calendar, path : Path)
    abstract fun getFastTraceEntity(faceTraceEntityId : Long?) : FastTraceEntity?

    fun tryCache(faceTraceEntityId: Long?) : List<LinkedSpecificationItem>? {
        if (cache.first != null && faceTraceEntityId != null && cache.first == faceTraceEntityId) {
            return cache.second
        }
        return null
    }

    fun cacheSpecItems(faceTraceEntityId: Long?, items : List<LinkedSpecificationItem>) {
        if (faceTraceEntityId != null) {
            cache = Pair(faceTraceEntityId, items)
        }
    }
}
