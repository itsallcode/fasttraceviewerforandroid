package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.lifecycle.LiveData
import javax.inject.Inject

import itsallcode.org.fasttraceviewerforandroid.data.FastTraceDao
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import java.nio.file.Path
import java.util.Calendar

/**
 * Created by thomasu on 2/2/18.
 */

class FastTraceDataSource @Inject
constructor(private val mFastTraceDao: FastTraceDao) : FastTraceRepository() {

    override fun add(name: String, creationData: Calendar, path : Path) {
        val entity = FastTraceEntity(name, creationData, path)
        mFastTraceDao.insert(entity)
    }

    override fun getFastTraceEntity(faceTraceEntityId : Long?) : FastTraceEntity? {
        return if (faceTraceEntityId == null) null else mFastTraceDao.getSpecific(faceTraceEntityId)
    }

    override val allFastTraceItems : LiveData<List<FastTraceEntity>>
        get() = mFastTraceDao.all


    companion object {
        private const val TAG = "FastTraceDataSource"
    }
}
