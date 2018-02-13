package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.util.Log

import javax.inject.Inject

import itsallcode.org.fasttraceviewerforandroid.data.DependentSpecificationItemDao
import itsallcode.org.fasttraceviewerforandroid.data.FastTraceDao
import itsallcode.org.fasttraceviewerforandroid.data.FastTraceSpecItemMappingDao
import itsallcode.org.fasttraceviewerforandroid.data.SpecificationItemCoverDao
import itsallcode.org.fasttraceviewerforandroid.data.SpecificationItemDao
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceSpecItemMappingEntity
import itsallcode.org.fasttraceviewerforandroid.repository.entities.SpecificationItemEntity
import openfasttrack.core.SpecificationItem
import java.util.*
import java.util.stream.Collectors.toList

/**
 * Created by thomasu on 2/2/18.
 */

class FastTraceDataSource @Inject
constructor(val mFastTraceDao: FastTraceDao,
            val mSpecificationItemDao: SpecificationItemDao,
            val mSpecificationItemCoverDao: SpecificationItemCoverDao,
            val mFastTraceSpecItemMappingDao: FastTraceSpecItemMappingDao,
            val mDependentSpecificationItemDao: DependentSpecificationItemDao) : FastTraceRepository {

    override fun add(name: String, creationData: Calendar, itemList: List<SpecificationItem>) {
        val entity = FastTraceEntity(name, creationData)
        val id = mFastTraceDao.insert(entity)
        itemList.forEach{
            val specificationItemEntity = SpecificationItemEntity(it.id, it.title,
                    it.description, it.rationale,
                    it.comment)
            val globalSpecId = mSpecificationItemDao.insert(specificationItemEntity)
            mFastTraceSpecItemMappingDao.insert(FastTraceSpecItemMappingEntity(id, globalSpecId))
        }
    }

    override fun getAllSpecItems(faceTraceEntityId : Long) : LiveData<List<SpecItem>>{
        val observableSpecItems: MediatorLiveData<List<SpecItem>> = MediatorLiveData()
        observableSpecItems.setValue(null)
        // observe the changes of the products from the database and forward them
        observableSpecItems.addSource(
                mFastTraceSpecItemMappingDao.getSpecItemsOfFastTraceEntity(faceTraceEntityId),
                { updateSpecItems(it, observableSpecItems) })

        return observableSpecItems
    }

    private fun updateSpecItems(items : List<SpecificationItemEntity>?,
                                merger : MediatorLiveData<List<SpecItem>>) {
        Log.d(TAG, "updateSpecItems: size=" + items?.size)
        val specItems : List<SpecItem>  =
                items?.let {
                    it.stream().map {
                        SpecItem(it.specificationItemGlobalId ?: 0, SpecificationItem.Builder()
                                .title(it.title)
                                .description(it.description)
                                .comment(it.comment)
                                .id(it.specificationItemId).build())
                    }.collect(toList())
                } ?: emptyList()
        merger.value = specItems
    }

    override val allFastTraceItems : LiveData<List<FastTraceEntity>>
        get() = mFastTraceDao.all

    companion object {
        private const val TAG = "FastTraceDataSource"
    }
}
