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

package itsallcode.org.fasttraceviewerforandroid.repository.typeconverters

import android.arch.persistence.room.TypeConverter
import openfasttrack.core.SpecificationItemId
import java.util.*

/**
 * Created by thomasu on 2/3/18.
 */
class CalendarConverter {

    @TypeConverter
    fun fromCalendar(value: Calendar?): Long? {
        return value?.timeInMillis
    }

    @TypeConverter
    fun toCalendar(value: Long?): Calendar? {
        return if (value == null) null else {
            val cal : Calendar = Calendar.getInstance()
            cal.timeInMillis = value
            return cal
        }
    }
}
