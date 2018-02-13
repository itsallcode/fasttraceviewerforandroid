package itsallcode.org.fasttraceviewerforandroid.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem
import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository
import javax.inject.Inject

/**
 * Created by thomasu on 2/4/18.
 */
open class SpecListViewModel : ViewModel() {

    @Inject lateinit var fastTraceRepository : FastTraceRepository

    fun getSpecListItems(fastTraceEntityId : Long) : LiveData<List<SpecItem>> {
        return fastTraceRepository.getAllSpecItems(fastTraceEntityId)
    }

    companion object {
        private val TAG : String = "FastTraceViewModel"
    }
}