package itsallcode.org.fasttraceviewerforandroid.repository.entities

import android.arch.persistence.room.Entity
import openfasttrack.core.SpecificationItemId

import android.arch.persistence.room.PrimaryKey

/**
 * Created by thomasu on 2/2/18.
 */

@Entity
class SpecificationItemEntity(val specificationItemId: SpecificationItemId,
                              val title: String,
                              val description: String,
                              val rationale: String,
                              val comment: String) {
    @PrimaryKey(autoGenerate = true)
    var specificationItemGlobalId : Long? = null
}