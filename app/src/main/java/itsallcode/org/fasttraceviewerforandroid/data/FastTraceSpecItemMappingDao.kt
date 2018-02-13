package itsallcode.org.fasttraceviewerforandroid.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceSpecItemMappingEntity

import itsallcode.org.fasttraceviewerforandroid.repository.entities.SpecificationItemCoverEntity
import itsallcode.org.fasttraceviewerforandroid.repository.entities.SpecificationItemEntity
import openfasttrack.core.SpecificationItemId

/**
 * Created by thomasu on 2/2/18.
 */
@Dao
interface FastTraceSpecItemMappingDao {
    /**
     * Inserts new FastTraceSpecItemMappingDao
     * @param entity Entities
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FastTraceSpecItemMappingEntity)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM SpecificationItemEntity INNER JOIN FastTraceSpecItemMappingEntity ON" +
                    " FastTraceSpecItemMappingEntity.specId=SpecificationItemEntity.specificationItemGlobalId" +
                    " WHERE FastTraceSpecItemMappingEntity.fastTraceId = :FastTraceEntityId ")
    fun getSpecItemsOfFastTraceEntity(FastTraceEntityId: Long): LiveData<List<SpecificationItemEntity>>
}
