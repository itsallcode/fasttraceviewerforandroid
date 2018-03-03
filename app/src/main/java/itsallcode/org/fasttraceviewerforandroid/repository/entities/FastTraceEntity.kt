/*-
 * #%L
 \* FastTraceViewerForAndroid
 * %%
 * Copyright (C) 2017 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package itsallcode.org.fasttraceviewerforandroid.repository.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import itsallcode.org.fasttraceviewerforandroid.ui.model.FastTraceItem
import java.nio.file.Path
import java.util.*

/**
 * Created by thomasu on 2/2/18.
 */

@Entity
class FastTraceEntity(override val name: String, override val creationDate: Calendar, val path : Path)
    : FastTraceItem {
    @PrimaryKey(autoGenerate = true)
    override var id: Long? = null
}
