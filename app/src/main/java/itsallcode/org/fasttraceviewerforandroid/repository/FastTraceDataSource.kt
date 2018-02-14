package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.util.Log
import com.uebensee.thomasu.fasttrackmobile.util.async

import javax.inject.Inject

import itsallcode.org.fasttraceviewerforandroid.data.FastTraceDao
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import openfasttrack.core.LinkedSpecificationItem
import openfasttrack.core.Linker
import openfasttrack.importer.ImporterService
import java.nio.file.Path
import java.util.Calendar

/**
 * Created by thomasu on 2/2/18.
 */

class FastTraceDataSource @Inject
constructor(private val mFastTraceDao: FastTraceDao) : FastTraceRepository {
    private var cache = Pair<Long?, List<LinkedSpecificationItem>?>(null, null)

    override fun add(name: String, creationData: Calendar, path : Path) {
        val entity = FastTraceEntity(name, creationData, path)
        mFastTraceDao.insert(entity)
    }

    override fun getFastTraceEntity(faceTraceEntityId : Long?) : FastTraceEntity? {
        return if (faceTraceEntityId == null) null else mFastTraceDao.getSpecific(faceTraceEntityId)
    }

    override val allFastTraceItems : LiveData<List<FastTraceEntity>>
        get() = mFastTraceDao.all

    override fun tryCache(faceTraceEntityId: Long?) : List<LinkedSpecificationItem>? {
        if (cache.first != null && faceTraceEntityId != null && cache.first == faceTraceEntityId) {
            return cache.second
        }
        return null
    }
    override fun cacheSpecItems(faceTraceEntityId: Long?, items : List<LinkedSpecificationItem>) {
        if (faceTraceEntityId != null) {
            cache = Pair(faceTraceEntityId, items)
        }
    }


    companion object {
        private const val TAG = "FastTraceDataSource"
    }
}
