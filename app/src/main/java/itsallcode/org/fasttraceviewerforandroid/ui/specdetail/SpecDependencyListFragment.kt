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

package itsallcode.org.fasttraceviewerforandroid.ui.specdetail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.databinding.DependencyListFragmentBinding
import itsallcode.org.fasttraceviewerforandroid.ui.StartActivity
import itsallcode.org.fasttraceviewerforandroid.ui.model.SpecItem
import itsallcode.org.fasttraceviewerforandroid.ui.speclist.SpecAdapter
import itsallcode.org.fasttraceviewerforandroid.ui.speclist.SpecClickCallback
import itsallcode.org.fasttraceviewerforandroid.viewmodel.SpecDetailViewModel
import openfasttrack.core.LinkStatus
import openfasttrack.core.SpecificationItemId

// Instances of this class are fragments representing a single
// object in our collection.
class SpecDependencyListFragment : Fragment() {

    private var mSpecAdapter: SpecAdapter? = null

    private var mBinding: DependencyListFragmentBinding? = null

    private var mLinkStatus : LinkStatus = LinkStatus.COVERS

    private val specDataObserver = Observer<List<SpecItem>> { updateSpecData(it) }

    private val mSpecClickCallback = object : SpecClickCallback {
        override fun onClick(item: SpecificationItemId) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                getViewModel()?.specItemLoaded?.value?.fastTraceEntityId?.let {
                    (activity as StartActivity).show(it, item)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(${mLinkStatus})")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // The last two arguments ensure LayoutParams are inflated
        // properly.
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.dependency_list_fragment, container, false)
        mLinkStatus = LinkStatus.values()[arguments?.getInt(ARG_LINK_STATUS) ?: 0]

        mSpecAdapter = SpecAdapter(mSpecClickCallback)
        mBinding?.coveredSpecItemList?.adapter = mSpecAdapter
        getViewModel()?.specItemDependencyListsLoadedMap
                ?.get(mLinkStatus)?.observe(this, specDataObserver)

        return mBinding?.root
    }

    private fun updateSpecData(depList: List<SpecItem>?) {
        Log.d(TAG, "updateSpecData (${mLinkStatus})")
        if (depList != null) {
            mSpecAdapter!!.setSpecItemList(depList)
        }
        // espresso does not know how to wait for data binding's loop so we execute changes
        // sync.
        mBinding!!.executePendingBindings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView (${mLinkStatus})")
        getViewModel()?.specItemDependencyListsLoadedMap
                ?.get(mLinkStatus)?.removeObserver(specDataObserver)
    }


    private fun getViewModel() : SpecDetailViewModel? {
        return (parentFragment as SpecDetailedFragment).mViewModel
    }

    companion object {
        const val ARG_LINK_STATUS = "link_status"
        private const val TAG = "SpecDependencyListFragment"
    }
}