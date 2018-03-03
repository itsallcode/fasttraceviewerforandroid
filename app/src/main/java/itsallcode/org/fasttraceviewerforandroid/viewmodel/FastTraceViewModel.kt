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

package itsallcode.org.fasttraceviewerforandroid.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.net.Uri
import android.util.Log
import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.SingleLiveEvent
import itsallcode.org.fasttraceviewerforandroid.platformaccess.AppExecutors
import itsallcode.org.fasttraceviewerforandroid.platformaccess.CacheAccess
import itsallcode.org.fasttraceviewerforandroid.platformaccess.ContentAccess
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import java.util.Calendar
import javax.inject.Inject

/**
 * Created by thomasu on 2/4/18.
 */
open class FastTraceViewModel : ViewModel() {
    val newTaskEvent = SingleLiveEvent<Void>()
    val snackbarMessage = SingleLiveEvent<Int>()

    @Inject lateinit var fastTraceRepository : FastTraceRepository
    @Inject lateinit var cacheAccess : CacheAccess
    @Inject lateinit var contentAccess : ContentAccess
    @Inject lateinit var executors : AppExecutors

    fun getFastTraceItems() : LiveData<List<FastTraceEntity>> {
        return fastTraceRepository.allFastTraceItems
    }

    fun addFastTraceEntity() {
        newTaskEvent.call()
    }

    fun importFile(uri: Uri) {
        executors.bgExecutor.execute {
            showSnackbarMessage(R.string.copy_document)
            val inputStream = contentAccess.open(uri)
            val name = contentAccess.getName(uri)
            val file = cacheAccess.copyToCache(inputStream, name)
            val path = file.toPath()
            showSnackbarMessage(R.string.finish_copy_document)
            fastTraceRepository.add(name ?: "Unknown",
                    Calendar.getInstance(), path)
        }
    }

    private fun showSnackbarMessage(message: Int) {
        snackbarMessage.postValue(message)
    }

    companion object {
        private val TAG : String = "FastTraceViewModel"
    }
}