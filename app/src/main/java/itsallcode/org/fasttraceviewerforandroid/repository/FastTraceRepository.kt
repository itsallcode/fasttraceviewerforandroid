package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.lifecycle.LiveData

import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import openfasttrack.core.LinkedSpecificationItem
import java.nio.file.Path
import java.util.*

/**
 * Created by thomasu on 1/27/18.
 */

abstract class FastTraceRepository {
    private var cache = Pair<Long?, List<LinkedSpecificationItem>?>(null, null)

    abstract val allFastTraceItems: LiveData<List<FastTraceEntity>>
    abstract fun add(name: String, creationData: Calendar, path : Path)
    abstract fun getFastTraceEntity(faceTraceEntityId : Long?) : FastTraceEntity?

    fun tryCache(faceTraceEntityId: Long?) : List<LinkedSpecificationItem>? {
        if (cache.first != null && faceTraceEntityId != null && cache.first == faceTraceEntityId) {
            return cache.second
        }
        return null
    }

    fun cacheSpecItems(faceTraceEntityId: Long?, items : List<LinkedSpecificationItem>) {
        if (faceTraceEntityId != null) {
            cache = Pair(faceTraceEntityId, items)
        }
    }
}
