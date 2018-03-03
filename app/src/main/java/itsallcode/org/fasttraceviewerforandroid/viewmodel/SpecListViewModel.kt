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

import android.arch.lifecycle.ViewModel
import android.util.Log
import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.SingleLiveEvent
import itsallcode.org.fasttraceviewerforandroid.ui.model.TraceItem
import itsallcode.org.fasttraceviewerforandroid.platformaccess.AppExecutors
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository
import itsallcode.org.fasttraceviewerforandroid.ui.model.SpecItem
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
    val errorMessage = SingleLiveEvent<String>()
    val mTraceItemsLoaded: SingleLiveEvent<TraceItem> = SingleLiveEvent()

    fun loadSpecListItems(fastTraceEntityId: Long?) {
        loadSpecListItems(fastTraceEntityId, false)
    }

    private fun loadSpecListItems(fastTraceEntityId: Long?, filterOn : Boolean) {
        val cachedItems = fastTraceRepository.tryCache(fastTraceEntityId)?.run { applyFilter(this, filterOn) }
        if (fastTraceEntityId != null && cachedItems != null) {
            executors.bgExecutor.execute {
                val (specItems, statusIcon) = buildSpecItems(cachedItems)
                mTraceItemsLoaded.postValue(TraceItem(fastTraceEntityId, specItems, statusIcon))
            }
        } else {
            executors.bgExecutor.execute {
                val fastTraceEntity = fastTraceRepository.getFastTraceEntity(fastTraceEntityId)
                fastTraceEntity?.let {
                    showSnackbarMessage(R.string.loading_document)
                    try {
                    val rawData = ImporterService().importFile(it.path)
                    showSnackbarMessage(R.string.linking_document)
                    val linkedData = Linker(rawData).link().run { applyFilter(this, filterOn) }
                    val (specItems, statusIcon) = buildSpecItems(linkedData)
                    fastTraceRepository.cacheSpecItems(fastTraceEntityId, linkedData)
                    mTraceItemsLoaded.postValue(TraceItem(it.id ?: 0, specItems, statusIcon))
                    } catch(ex : IllegalStateException) {
                        showErrorMessage("Error reading file:" + ex.message)
                    }
                }
            }
        }
    }

    private fun applyFilter(items : List<LinkedSpecificationItem>, filterOn : Boolean) :
        List<LinkedSpecificationItem> {
        Log.d(TAG, "applyFilter")
        return if (filterOn) items.stream().filter { it.isDefect }.collect(toList()) else items
    }

    fun filter(fastTraceEntityId: Long?, filterOn : Boolean) {
        Log.d(TAG, "filter: fastTraceEntityId - " + fastTraceEntityId + " filterOn - " + filterOn )
        loadSpecListItems(fastTraceEntityId, filterOn)
    }

    private fun buildSpecItems(linkedSpecificationItems : List<LinkedSpecificationItem>) : Pair<List<SpecItem>, Int> {
        val specItems =  linkedSpecificationItems.stream().map { SpecItem(it.id,
                it.item.title, it.description,
                if (it.isDefect) R.mipmap.ic_no_ok else R.mipmap.ic_ok) }.collect(toList())
        linkedSpecificationItems.forEach { Log.d(TAG, "Title=" + it.item.title) }
        val statusIcon = if (linkedSpecificationItems.find { it.isDefect } == null) R.mipmap.ic_ok else R.mipmap.ic_no_ok
        return Pair(specItems, statusIcon)
    }

    private fun showSnackbarMessage(message: Int) {
        snackbarMessage.postValue(message)
    }

    private fun showErrorMessage(message: String) {
        errorMessage.postValue(message)
    }

    companion object {
        private val TAG: String = "FastTraceViewModel"
    }
}