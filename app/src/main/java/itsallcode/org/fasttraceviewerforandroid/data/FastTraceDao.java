package itsallcode.org.fasttraceviewerforandroid.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity;

/**
 * Created by thomasu on 2/2/18.
 */
@Dao
public interface FastTraceDao {
    /**
     * Inserts new FastDataEntity
     * @param entity Entities
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(final FastTraceEntity entity);

    @Query("SELECT * FROM FastTraceEntity")
    List<FastTraceEntity> getAll();

    @Query("SELECT * FROM FastTraceEntity WHERE mName = :name")
    FastTraceEntity getByName(final String name);
}
