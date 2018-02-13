package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.lifecycle.LiveData
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem

import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import openfasttrack.core.SpecificationItem
import java.util.*

/**
 * Created by thomasu on 1/27/18.
 */

interface FastTraceRepository {
    val allFastTraceItems: LiveData<List<FastTraceEntity>>
    fun add(name: String, creationData: Calendar, itemList: List<SpecificationItem>)
    fun getAllSpecItems(faceTraceEntityId : Long) : LiveData<List<SpecItem>>
}
