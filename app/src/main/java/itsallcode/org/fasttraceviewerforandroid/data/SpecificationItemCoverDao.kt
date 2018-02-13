package itsallcode.org.fasttraceviewerforandroid.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import itsallcode.org.fasttraceviewerforandroid.repository.entities.SpecificationItemCoverEntity

/**
 * Created by thomasu on 2/2/18.
 */
@Dao
interface SpecificationItemCoverDao {
    /**
     * Inserts new SpecificationItemDao
     * @param entity Entities
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SpecificationItemCoverEntity)

    @Query("SELECT coveredSpecId FROM SpecificationItemCoverEntity WHERE specId = :specId")
    fun getCoveredItems(specId: Int): List<Int>
}
