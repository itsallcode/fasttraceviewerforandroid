package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.util.Log
import com.uebensee.thomasu.fasttrackmobile.util.async

import javax.inject.Inject

import itsallcode.org.fasttraceviewerforandroid.data.FastTraceDao
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import openfasttrack.core.Linker
import openfasttrack.importer.ImporterService
import java.nio.file.Path
import java.util.Calendar

/**
 * Created by thomasu on 2/2/18.
 */

class FastTraceDataSource @Inject
constructor(val mFastTraceDao: FastTraceDao) : FastTraceRepository {

    override fun add(name: String, creationData: Calendar, path : Path) {
        val entity = FastTraceEntity(name, creationData, path)
        mFastTraceDao.insert(entity)
    }

    override fun getAllSpecItems(faceTraceEntityId : Long?) : LiveData<SpecItem>{
        val observableItem: MediatorLiveData<SpecItem> = MediatorLiveData()
        if (faceTraceEntityId != null) {
            observableItem.value = null
            // observe the changes of the products from the database and forward them
            observableItem.addSource(
                    mFastTraceDao.getSpecific(faceTraceEntityId),
                    { updateSpecItems(it, observableItem) })
        }
        return observableItem
    }

    private fun updateSpecItems(item : FastTraceEntity?,
                                merger : MediatorLiveData<SpecItem>) {
        Log.d(TAG, "updateSpecItems: path=" + item?.path)
        item?.let {
            val p = Pair(it, merger)
            async(p) {
                val rawData = ImporterService().importFile(p.first.path)
                val linkedData = Linker(rawData).link()
                p.second.postValue(SpecItem(p.first.id ?: 0, linkedData))
            }
        }
    }

    override val allFastTraceItems : LiveData<List<FastTraceEntity>>
        get() = mFastTraceDao.all

    companion object {
        private const val TAG = "FastTraceDataSource"
    }
}
