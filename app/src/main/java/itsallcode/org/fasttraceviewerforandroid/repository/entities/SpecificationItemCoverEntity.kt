package itsallcode.org.fasttraceviewerforandroid.repository.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

import openfasttrack.core.SpecificationItemId

import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index

/**
 * Created by thomasu on 2/2/18.
 */

@Entity(foreignKeys =
 [ForeignKey(entity = SpecificationItemEntity::class,
                        parentColumns = ["specificationItemGlobalId"],
                        childColumns = ["specId"],
                        onDelete = CASCADE),
  ForeignKey(entity = SpecificationItemEntity::class,
          parentColumns = ["specificationItemGlobalId"],
          childColumns = ["coveredSpecId"],
          onDelete = CASCADE)],
        primaryKeys = ["specId", "coveredSpecId"],
        indices = [(Index("coveredSpecId"))])
class SpecificationItemCoverEntity(val specId: Long,
                                   val coveredSpecId: Long)
