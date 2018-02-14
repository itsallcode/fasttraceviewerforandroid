package itsallcode.org.fasttraceviewerforandroid.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.uebensee.thomasu.fasttrackmobile.util.async
import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.SingleLiveEvent
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceDataSource
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import openfasttrack.core.LinkedSpecificationItem
import openfasttrack.core.Linker
import openfasttrack.importer.ImporterService
import javax.inject.Inject

/**
 * Created by thomasu on 2/4/18.
 */
open class SpecListViewModel : ViewModel() {

    @Inject
    lateinit var fastTraceRepository: FastTraceRepository
    val snackbarMessage = SingleLiveEvent<Int>()
    val specItemsLoaded: SingleLiveEvent<SpecItem> = SingleLiveEvent()

    fun loadSpecListItems(fastTraceEntityId: Long?) {
        val cachedItems = fastTraceRepository.tryCache(fastTraceEntityId)
        if (fastTraceEntityId != null && cachedItems != null) {
            specItemsLoaded.value = SpecItem(fastTraceEntityId, cachedItems)
        } else {
            async(fastTraceEntityId) {
                val fastTraceEntity = fastTraceRepository.getFastTraceEntity(it)
                fastTraceEntity?.let {
                    showSnackbarMessage(R.string.loading_document)
                    val rawData = ImporterService().importFile(it.path)
                    showSnackbarMessage(R.string.linking_document)
                    val linkedData = Linker(rawData).link()
                    specItemsLoaded.postValue(SpecItem(it.id ?: 0, linkedData))
                }
            }
        }
    }

    private fun showSnackbarMessage(message: Int) {
        snackbarMessage.postValue(message)
    }


    companion object {
        private val TAG: String = "FastTraceViewModel"
    }
}