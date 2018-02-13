package itsallcode.org.fasttraceviewerforandroid.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity

/**
 * Created by thomasu on 2/2/18.
 */
@Dao
interface FastTraceDao {

    @get:Query("SELECT * FROM FastTraceEntity")
    val all: LiveData<List<FastTraceEntity>>

    /**
     * Inserts new FastDataEntity
     * @param entity Entities
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FastTraceEntity) : Long
}
