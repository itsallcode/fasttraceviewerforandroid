package itsallcode.org.fasttraceviewerforandroid.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import itsallcode.org.fasttraceviewerforandroid.repository.entities.SpecificationItemEntity
import openfasttrack.core.SpecificationItemId

/**
 * Created by thomasu on 2/2/18.
 */
@Dao
interface SpecificationItemDao {
    /**
     * Inserts new SpecificationItemDao
     * @param entity Entities
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SpecificationItemEntity) : Long

    @Query("SELECT * FROM SpecificationItemEntity WHERE specificationItemGlobalId = :specId")
    fun getSpecific(specId: Int): SpecificationItemEntity
}
