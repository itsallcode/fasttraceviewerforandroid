package itsallcode.org.fasttraceviewerforandroid.repository.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index

/**
 * Created by thomasu on 2/2/18.
 */

@Entity( foreignKeys =
 [(ForeignKey(entity = FastTraceEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("fastTraceId"),
                onDelete = CASCADE)),
     (ForeignKey(entity = SpecificationItemEntity::class,
             parentColumns = arrayOf("specificationItemGlobalId"),
             childColumns = arrayOf("specId"),
             onDelete = CASCADE))],
        primaryKeys = ["fastTraceId", "specId"],
        indices = [(Index("specId"))])
class FastTraceSpecItemMappingEntity(val fastTraceId: Long,
                                     val specId: Long)
