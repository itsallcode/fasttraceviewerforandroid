package itsallcode.org.fasttraceviewerforandroid.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.net.Uri
import android.util.Log
import com.uebensee.thomasu.fasttrackmobile.util.async
import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.SingleLiveEvent
import itsallcode.org.fasttraceviewerforandroid.platformaccess.CacheAccess
import itsallcode.org.fasttraceviewerforandroid.platformaccess.ContentAccess
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import openfasttrack.core.SpecificationItem
import openfasttrack.importer.ImporterService
import java.io.InputStream
import java.util.*
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

    fun getFastTraceItems() : LiveData<List<FastTraceEntity>> {
        return fastTraceRepository.allFastTraceItems
    }

    fun addFastTraceEntity() {
        newTaskEvent.call()
    }

    fun importFile(uri: Uri) {
        async(uri) {
            showSnackbarMessage(R.string.copy_document)
            val inputStream = contentAccess.open(it)
            val name = contentAccess.getName(it)
            val path = cacheAccess.copyToCache(inputStream, name).toPath()
            showSnackbarMessage(R.string.finish_copy_document)
            Log.d(TAG, "Importing: " + path)
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