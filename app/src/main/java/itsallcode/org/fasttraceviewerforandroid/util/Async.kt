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

package com.uebensee.thomasu.fasttrackmobile.util

import android.os.AsyncTask

class SomeTask<P> constructor(private val predicate: (P) -> Unit) : AsyncTask<P, Unit, Unit>() {
    override fun doInBackground(vararg params: P?) {
        if (params.size == 1) {
            params[0]?.let { this.predicate(it) }
        }
    }
}

/**
 * Created by thomasu on 12/23/17.
 */
fun <P> async(p: P, predicate: (P) -> Unit) {
    val t = SomeTask(predicate)
    t.execute(p)
}
