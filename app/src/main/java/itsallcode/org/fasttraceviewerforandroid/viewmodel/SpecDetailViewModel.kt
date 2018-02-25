package itsallcode.org.fasttraceviewerforandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.SingleLiveEvent
import itsallcode.org.fasttraceviewerforandroid.platformaccess.AppExecutors
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository
import itsallcode.org.fasttraceviewerforandroid.ui.model.DetailedSpecItem
import itsallcode.org.fasttraceviewerforandroid.ui.model.SpecItem
import openfasttrack.core.*
import openfasttrack.importer.ImporterService
import java.util.*
import java.util.stream.Collectors.toList
import javax.inject.Inject

/**
 * Created by thomasu on 2/4/18.
 */
open class SpecDetailViewModel : ViewModel() {

    @Inject
    lateinit var fastTraceRepository: FastTraceRepository
    @Inject
    lateinit var executors: AppExecutors

    val snackbarMessage = SingleLiveEvent<Int>()
    val specItemLoaded: SingleLiveEvent<DetailedSpecItem> = SingleLiveEvent()
    val specItemDependencyListsLoadedMap =
            EnumMap<LinkStatus, MutableLiveData<List<SpecItem>>>(LinkStatus::class.java)
    init {
        LinkStatus.values().forEach { it -> specItemDependencyListsLoadedMap[it] = MutableLiveData() }
    }

    fun loadSpecListItems(fastTraceEntityId: Long?, specItemId: String) {
        val cachedItems = fastTraceRepository.tryCache(fastTraceEntityId)
        if (fastTraceEntityId != null && cachedItems != null) {
            executors.bgExecutor.execute {
                notify(fastTraceEntityId, cachedItems, specItemId)
            }
        } else {
            executors.bgExecutor.execute {
                val fastTraceEntity = fastTraceRepository.getFastTraceEntity(fastTraceEntityId)
                fastTraceEntity?.let {
                    showSnackbarMessage(R.string.loading_document)
                    val rawData = ImporterService().importFile(it.path)
                    showSnackbarMessage(R.string.linking_document)
                    val linkedData = Linker(rawData).link()
                    fastTraceRepository.cacheSpecItems(fastTraceEntityId, linkedData)
                    notify(fastTraceEntityId, linkedData, specItemId)
                }
            }
        }
    }

    private fun notify(fastTraceEntityId: Long?,
                       items : List<LinkedSpecificationItem>,
                       specItemId: String) {
        val linkedSpecificationItem =
                items.find { it -> it.id == SpecificationItemId.parseId(specItemId) }

        specItemLoaded.postValue(buildSpecItem(fastTraceEntityId, linkedSpecificationItem))
        LinkStatus.values().forEach {
            val linkSpecItems = linkedSpecificationItem?.getLinksByStatus(it)
            if (linkSpecItems != null) {
                specItemDependencyListsLoadedMap[it]?.postValue(buildSpecItems(linkSpecItems))
            }
        }

    }

    private fun buildSpecItem(fastTraceEntityId: Long?,
                              linkedSpecificationItem : LinkedSpecificationItem?)
            : DetailedSpecItem? {
        if (linkedSpecificationItem != null && fastTraceEntityId != null) {
            return DetailedSpecItem(fastTraceEntityId,
                    linkedSpecificationItem)
        }
        return null
    }

    private fun buildSpecItems(linkedSpecificationItems: List<LinkedSpecificationItem>)
        : List<SpecItem> {
        return linkedSpecificationItems.stream()
                .map { it -> SpecItem(it.id, it.item.title, it.description,
                if (it.isDefect) R.mipmap.ic_no_ok else R.mipmap.ic_ok)}.collect(toList())
    }

    private fun showSnackbarMessage(message: Int) {
        snackbarMessage.postValue(message)
    }

    companion object {
        private val TAG: String = "SpecDetailViewModel"
    }
}