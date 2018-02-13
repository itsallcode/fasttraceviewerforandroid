package itsallcode.org.fasttraceviewerforandroid.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import itsallcode.org.fasttraceviewerforandroid.data.*
import itsallcode.org.fasttraceviewerforandroid.repository.entities.*
import itsallcode.org.fasttraceviewerforandroid.repository.typeconverters.CalendarConverter

import itsallcode.org.fasttraceviewerforandroid.repository.typeconverters.SpecificationItemIdConverter

/**
 * Created by thomasu on 2/2/18.
 */

@Database(entities = [FastTraceEntity::class,
                        FastTraceSpecItemMappingEntity::class,
                        SpecificationItemEntity::class,
                        SpecificationItemCoverEntity::class,
                        DependentSpecificationItemEntity::class],
            version = FastTraceDatabase.DATABASE_VERSION,
            exportSchema = false)
@TypeConverters(SpecificationItemIdConverter::class, CalendarConverter::class)
abstract class FastTraceDatabase : RoomDatabase() {

    /**
     * Gets the FastTrace Dao used to access all available workspace.
     *
     * @return FastTrace Dao used on the database.
     */
    abstract fun fastTraceDao(): FastTraceDao

    /**
     * Gets the Specification Item Dao used to access all available SpecificationItems.
     *
     * @return Specification Item used on the database.
     */
    abstract fun specificationItemDao(): SpecificationItemDao

    /**
     * Gets the Specification Item Cover Dao used to access all available SpecificationItems cover.
     *
     * @return Specification Item Covering used on the database.
     */
    abstract fun specificationItemCoverDao(): SpecificationItemCoverDao

    /**
     * Gets the Specification Item Mapping Dao used to access all available SpecificationItems for fasttrace entity.
     *
     * @return Specification Items for FastTrace entity.
     */
    abstract fun fastTraceMappingDao(): FastTraceSpecItemMappingDao

    /**
     * Gets the Specification Item Mapping Dao used to access all available SpecificationItems for fasttrace entity.
     *
     * @return Specification Items for FastTrace entity.
     */
    abstract fun dependentSpecificationItemDao(): DependentSpecificationItemDao

    companion object {

        /**
         * Database name.
         */
        const val DATABASE_NAME = "fast_trace_database.db"

        /**
         * Database version.
         */
        const val DATABASE_VERSION = 1
    }
}
