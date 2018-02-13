package itsallcode.org.fasttraceviewerforandroid.repository.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import itsallcode.org.fasttraceviewerforandroid.model.FastTraceItem
import java.util.*

/**
 * Created by thomasu on 2/2/18.
 */

@Entity
class FastTraceEntity(override val name: String, override val creationDate: Calendar) : FastTraceItem {
    @PrimaryKey(autoGenerate = true)
    override var id: Long? = null
}
