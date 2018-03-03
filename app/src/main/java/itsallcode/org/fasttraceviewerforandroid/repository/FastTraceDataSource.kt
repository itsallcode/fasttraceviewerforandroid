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
import javax.inject.Inject

import itsallcode.org.fasttraceviewerforandroid.data.FastTraceDao
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import java.nio.file.Path
import java.util.Calendar

/**
 * Created by thomasu on 2/2/18.
 */

class FastTraceDataSource @Inject
constructor(private val mFastTraceDao: FastTraceDao) : FastTraceRepository() {

    override fun add(name: String, creationData: Calendar, path : Path) {
        val entity = FastTraceEntity(name, creationData, path)
        mFastTraceDao.insert(entity)
    }

    override fun getFastTraceEntity(faceTraceEntityId : Long?) : FastTraceEntity? {
        return if (faceTraceEntityId == null) null else mFastTraceDao.getSpecific(faceTraceEntityId)
    }

    override val allFastTraceItems : LiveData<List<FastTraceEntity>>
        get() = mFastTraceDao.all


    companion object {
        private const val TAG = "FastTraceDataSource"
    }
}
