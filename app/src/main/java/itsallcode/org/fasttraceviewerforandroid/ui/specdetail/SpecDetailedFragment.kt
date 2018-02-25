package itsallcode.org.fasttraceviewerforandroid.ui.specdetail

import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log


import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.FastTraceApp
import itsallcode.org.fasttraceviewerforandroid.util.setupSnackbar
import android.view.*
import itsallcode.org.fasttraceviewerforandroid.databinding.SpecDetailFragmentBinding
import itsallcode.org.fasttraceviewerforandroid.ui.model.DetailedSpecItem
import itsallcode.org.fasttraceviewerforandroid.viewmodel.SpecDetailViewModel
import openfasttrack.core.SpecificationItemId
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.view.ViewPager


/**
 * Created by thomasu on 2/4/18.
 */

class SpecDetailedFragment : Fragment() {

    private var mBinding: SpecDetailFragmentBinding? = null

    var mViewModel : SpecDetailViewModel? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.spec_detail_fragment,
                container, false)
        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        mViewModel = ViewModelProviders.of(this).get(SpecDetailViewModel::class.java)
                .apply {
                    FastTraceApp.getInstance().applicationComponent.inject(this)
                    subscribeUi(this)}
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        val dependencyListPagerAdapter = SpecDependencyPagerAdapter(childFragmentManager)
        val viewPager = mBinding?.root?.findViewById (R.id.pager) as ViewPager
        viewPager.adapter = dependencyListPagerAdapter

    }

    private fun subscribeUi(viewModel: SpecDetailViewModel) {
        // Update the list when the data changes
        viewModel.specItemLoaded.observe(this, Observer { updateSpecData(it) })
        mBinding!!.isLoading = true
        view?.setupSnackbar(this@SpecDetailedFragment,
                viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
        mBinding?.viewModel = viewModel
        viewModel.loadSpecListItems(arguments?.getLong(KEY_FAST_TRACE_ENTITY),
                arguments?.getString(KEY_FAST_SPEC_ID) ?: "")
    }

    private fun updateSpecData(detailedSpecItem: DetailedSpecItem?) {
        mBinding?.let {
            it.isLoading = (detailedSpecItem == null)
            it.invalidateAll()
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            it.executePendingBindings()
        }
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