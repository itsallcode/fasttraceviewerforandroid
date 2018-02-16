package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.lifecycle.MutableLiveData

import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import java.nio.file.Path
import java.util.*

/**
 * Created by thomasu on 1/27/18.
 */

internal class FastTraceTestRepository : FastTraceRepository() {

    private val entityList = mutableListOf<FastTraceEntity>()

    override val allFastTraceItems = MutableLiveData<List<FastTraceEntity>>()

    override fun add(name: String, creationData: Calendar, path : Path) {
        entityList.add(FastTraceEntity(name, creationData, path))
        allFastTraceItems.value = entityList
    }

    override fun getFastTraceEntity(faceTraceEntityId : Long?) : FastTraceEntity? {
        return if (faceTraceEntityId == null) null else entityList[faceTraceEntityId.toInt()]
    }
}
