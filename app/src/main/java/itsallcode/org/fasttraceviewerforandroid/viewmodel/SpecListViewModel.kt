package itsallcode.org.fasttraceviewerforandroid.viewmodel

import android.arch.lifecycle.ViewModel
import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.SingleLiveEvent
import itsallcode.org.fasttraceviewerforandroid.ui.model.TraceItem
import itsallcode.org.fasttraceviewerforandroid.platformaccess.AppExecutors
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository
import openfasttrack.core.LinkedSpecificationItem
import openfasttrack.core.Linker
import openfasttrack.importer.ImporterService
import java.util.stream.Collectors.toList
import javax.inject.Inject

/**
 * Created by thomasu on 2/4/18.
 */
open class SpecListViewModel : ViewModel() {

    @Inject
    lateinit var fastTraceRepository: FastTraceRepository
    @Inject
    lateinit var executors: AppExecutors

    val snackbarMessage = SingleLiveEvent<Int>()
    val mTraceItemsLoaded: SingleLiveEvent<TraceItem> = SingleLiveEvent()

    fun loadSpecListItems(fastTraceEntityId: Long?) {
        val cachedItems = fastTraceRepository.tryCache(fastTraceEntityId)
        if (fastTraceEntityId != null && cachedItems != null) {
            val (specItems, statusIcon) = buildSpecItems(cachedItems)
            mTraceItemsLoaded.value = TraceItem(fastTraceEntityId, specItems, statusIcon)
        } else {
            executors.bgExecutor.execute {
                val fastTraceEntity = fastTraceRepository.getFastTraceEntity(fastTraceEntityId)
                fastTraceEntity?.let {
                    showSnackbarMessage(R.string.loading_document)
                    val rawData = ImporterService().importFile(it.path)
                    showSnackbarMessage(R.string.linking_document)
                    val linkedData = Linker(rawData).link()
                    val (specItems, statusIcon) = buildSpecItems(linkedData)
                    mTraceItemsLoaded.postValue(TraceItem(it.id ?: 0, specItems, statusIcon))
                }
            }
        }
    }

    private fun buildSpecItems(linkedSpecificationItems : List<LinkedSpecificationItem>) : Pair<List<TraceItem.SpecItem>, Int> {
        val specItems =  linkedSpecificationItems.stream().map { TraceItem.SpecItem(it.id,
                it.item.title, it.description,
                if (it.isDefect) R.mipmap.ic_no_ok else R.mipmap.ic_ok) }.collect(toList())
        val statusIcon = if (linkedSpecificationItems.find { it.isDefect } == null) R.mipmap.ic_ok else R.mipmap.ic_no_ok
        return Pair(specItems, statusIcon)
    }

    private fun showSnackbarMessage(message: Int) {
        snackbarMessage.postValue(message)
    }


    companion object {
        private val TAG: String = "FastTraceViewModel"
    }
}