package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.lifecycle.LiveData
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem

import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import openfasttrack.core.LinkedSpecificationItem
import java.nio.file.Path
import java.util.*

/**
 * Created by thomasu on 1/27/18.
 */

interface FastTraceRepository {
    val allFastTraceItems: LiveData<List<FastTraceEntity>>
    fun add(name: String, creationData: Calendar, path : Path)
    fun getFastTraceEntity(faceTraceEntityId : Long?) : FastTraceEntity?
    fun tryCache(faceTraceEntityId: Long?) : List<LinkedSpecificationItem>?
    fun cacheSpecItems(faceTraceEntityId: Long?, items : List<LinkedSpecificationItem>)
}
