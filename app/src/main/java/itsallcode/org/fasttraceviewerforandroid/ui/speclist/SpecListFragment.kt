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

package itsallcode.org.fasttraceviewerforandroid.ui.speclist

import android.support.v4.app.Fragment
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log


import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.FastTraceApp
import itsallcode.org.fasttraceviewerforandroid.databinding.SpecListFragmentBinding
import itsallcode.org.fasttraceviewerforandroid.ui.model.TraceItem
import itsallcode.org.fasttraceviewerforandroid.ui.StartActivity
import itsallcode.org.fasttraceviewerforandroid.util.setupSnackbar
import itsallcode.org.fasttraceviewerforandroid.viewmodel.SpecListViewModel
import android.view.*
import android.widget.Toast
import openfasttrack.core.SpecificationItemId

/**
 * Fragment responsible displaying SpecList item view.
 */

class SpecListFragment : Fragment() {

    private var mSpecAdapter: SpecAdapter? = null

    private var mBinding: SpecListFragmentBinding? = null

    private var mViewModel : SpecListViewModel? = null

    private val mSpecClickCallback = object : SpecClickCallback {
        override fun onClick(item: SpecificationItemId) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                mViewModel?.mTraceItemsLoaded?.value?.fastTraceEntityId?.let {
                    (activity as StartActivity).show(it, item)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.spec_list_fragment, container, false)

        mSpecAdapter = SpecAdapter(mSpecClickCallback)
        mBinding?.specItemList?.adapter = mSpecAdapter

        return mBinding?.root
    }

    override fun onCreateOptionsMenu( menu : Menu, inflater : MenuInflater ) {
        inflater.inflate(R.menu.spec_list_menu, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        mViewModel = ViewModelProviders.of(this).get(SpecListViewModel::class.java)
                .apply {
                    FastTraceApp.getInstance().applicationComponent.inject(this)
                    subscribeUi(this)}
    }

    private fun subscribeUi(viewModel: SpecListViewModel) {
        // Update the list when the data changes
        viewModel.mTraceItemsLoaded.observe(this, Observer { updateSpecList(it) })
        viewModel.errorMessage.observe(this, Observer { showErrorMessage(it) })
        mBinding!!.isLoading = true
        view?.setupSnackbar(this@SpecListFragment,
                viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
        mBinding?.viewModel = viewModel
        viewModel.loadSpecListItems(arguments?.getLong(KEY_FAST_TRACE_ENTITY))
    }

    private fun showErrorMessage(error : String?) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    private fun updateSpecList(traceList: TraceItem?) {
        if (traceList != null) {
            mBinding!!.isLoading = false
            mSpecAdapter!!.setSpecItemList(traceList.items)
        } else {
            mBinding!!.isLoading = true
        }
        mBinding!!.invalidateAll()
        // espresso does not know how to wait for data binding's loop so we execute changes
        // sync.
        mBinding!!.executePendingBindings()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter_defect -> {
                item.isChecked = !item.isChecked
                Log.d(TAG, "onOptionsItemSelected")
                mViewModel?.filter(arguments?.getLong(KEY_FAST_TRACE_ENTITY), item.isChecked)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    companion object {
        const val KEY_FAST_TRACE_ENTITY : String = "FT_ENTITY"
        private const val TAG = "SpecListFragment"

        fun forFastTraceEntity(fastTraceEntity : Long): SpecListFragment {
            val fragment = SpecListFragment()
            val args = Bundle()
            args.putLong(KEY_FAST_TRACE_ENTITY, fastTraceEntity)
            fragment.arguments = args
            return fragment
        }
    }
}
