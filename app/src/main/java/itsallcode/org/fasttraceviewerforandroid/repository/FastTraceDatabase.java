package itsallcode.org.fasttraceviewerforandroid.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import itsallcode.org.fasttraceviewerforandroid.data.FastTraceDao;
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity;

/**
 * Created by thomasu on 2/2/18.
 */

@Database(entities = {FastTraceEntity.class},
        version = FastTraceDatabase.DATABASE_VERSION, exportSchema = false)
public abstract class FastTraceDatabase extends RoomDatabase {

    /**
     * Database name.
     */
    public static final String DATABASE_NAME = "fast_trace_database.db";

    /**
     * Database version.
     */
    /*package protected*/ static final int DATABASE_VERSION = 1;

    /**
     * Gets the Accel Dao used to access the accel operations.
     *
     * @return Accel Dao used on the database.
     */
    public abstract FastTraceDao fastTraceDao();

}
