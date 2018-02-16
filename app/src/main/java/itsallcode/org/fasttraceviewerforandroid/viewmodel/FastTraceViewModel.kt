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