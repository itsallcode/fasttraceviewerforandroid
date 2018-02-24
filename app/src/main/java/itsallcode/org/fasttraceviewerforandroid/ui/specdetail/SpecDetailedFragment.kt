package itsallcode.org.fasttraceviewerforandroid.ui.specdetail

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
import itsallcode.org.fasttraceviewerforandroid.ui.StartActivity
import itsallcode.org.fasttraceviewerforandroid.util.setupSnackbar
import android.view.*
import itsallcode.org.fasttraceviewerforandroid.databinding.SpecDetailFragmentBinding
import itsallcode.org.fasttraceviewerforandroid.ui.model.DetailedSpecItem
import itsallcode.org.fasttraceviewerforandroid.viewmodel.SpecDetailViewModel
import openfasttrack.core.SpecificationItemId
import itsallcode.org.fasttraceviewerforandroid.ui.speclist.SpecClickCallback
import itsallcode.org.fasttraceviewerforandroid.ui.speclist.SpecAdapter

/**
 * Created by thomasu on 2/4/18.
 */

class SpecDetailedFragment : Fragment() {

    private var mSpecAdapter: SpecAdapter? = null

    private var mBinding: SpecDetailFragmentBinding? = null

    private var mViewModel : SpecDetailViewModel? = null

    private val mSpecClickCallback = object : SpecClickCallback {
        override fun onClick(item: SpecificationItemId) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                mViewModel?.specItemLoaded?.value?.fastTraceEntityId?.let {
                    (activity as StartActivity).show(it, item)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.spec_detail_fragment, container, false)

        mSpecAdapter = SpecAdapter(mSpecClickCallback)
        mBinding?.coveredSpecItemList?.adapter = mSpecAdapter

        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        mViewModel = ViewModelProviders.of(this).get(SpecDetailViewModel::class.java)
                .apply {
                    FastTraceApp.getInstance().applicationComponent.inject(this)
                    subscribeUi(this)}
    }

    private fun subscribeUi(viewModel: SpecDetailViewModel) {
        // Update the list when the data changes
        viewModel.specItemLoaded.observe(this, Observer { updateSpecList(it) })
        mBinding!!.isLoading = true
        view?.setupSnackbar(this@SpecDetailedFragment,
                viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
        mBinding?.viewModel = viewModel
        viewModel.loadSpecListItems(arguments?.getLong(KEY_FAST_TRACE_ENTITY),
                arguments?.getString(KEY_FAST_SPEC_ID) ?: "")
    }

    private fun updateSpecList(detailedSpecItem: DetailedSpecItem?) {
        if (detailedSpecItem != null) {
            mBinding!!.isLoading = false
            mSpecAdapter!!.setSpecItemList(detailedSpecItem.coveredItems)
        } else {
            mBinding!!.isLoading = true
        }
        mBinding!!.invalidateAll()
        // espresso does not know how to wait for data binding's loop so we execute changes
        // sync.
        mBinding!!.executePendingBindings()
    }

    companion object {
        private const val KEY_FAST_TRACE_ENTITY : String = "FT_ENTITY"
        private const val KEY_FAST_SPEC_ID : String = "SPEC_ID"
        private const val TAG = "SpecListFragment"

        fun forSpecItem(fastTraceEntity : Long, specificationItemId: SpecificationItemId)
                : SpecDetailedFragment {
            val fragment = SpecDetailedFragment()
            val args = Bundle()
            args.putLong(KEY_FAST_TRACE_ENTITY, fastTraceEntity)
            args.putString(KEY_FAST_SPEC_ID, specificationItemId.toString())
            fragment.arguments = args
            return fragment
        }
    }
}
