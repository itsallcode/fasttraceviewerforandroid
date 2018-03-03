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

package itsallcode.org.fasttraceviewerforandroid.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.ui.model.FastTraceItem
import itsallcode.org.fasttraceviewerforandroid.ui.fasttraceentities.FastTraceListFragment
import itsallcode.org.fasttraceviewerforandroid.ui.specdetail.SpecDetailedFragment
import itsallcode.org.fasttraceviewerforandroid.ui.speclist.SpecListFragment
import openfasttrack.core.SpecificationItemId

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Add fast trace item list fragment if this is first creation
        if (savedInstanceState == null) {

            setContentView(R.layout.activity_start)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            FastTraceListFragment(), null).commit()
        }
    }

    /** Shows the spec list fragment  */
    fun show(fastTraceItem: FastTraceItem) {

                fastTraceItem.id?.let {
                    val specListFragment = SpecListFragment.forFastTraceEntity(it)
                    supportFragmentManager
                            .beginTransaction()
                            .addToBackStack("entity")
                            .replace(R.id.fragment_container,
                                    specListFragment, null).commit()
                }
    }

    fun show(fastTraceId : Long, specificationItemId: SpecificationItemId) {

        val specDetailedFragment = SpecDetailedFragment.forSpecItem(fastTraceId, specificationItemId)
        supportFragmentManager
                .beginTransaction()
                .addToBackStack("entity")
                .replace(R.id.fragment_container,
                        specDetailedFragment, null).commit()
    }
}
