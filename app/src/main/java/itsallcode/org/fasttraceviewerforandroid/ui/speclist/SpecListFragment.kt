package itsallcode.org.fasttraceviewerforandroid.ui.speclist

import android.support.v4.app.Fragment
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.FastTraceApp
import itsallcode.org.fasttraceviewerforandroid.databinding.SpecListFragmentBinding
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem
import itsallcode.org.fasttraceviewerforandroid.ui.StartActivity
import itsallcode.org.fasttraceviewerforandroid.viewmodel.SpecListViewModel


/**
 * Created by thomasu on 2/4/18.
 */

class SpecListFragment : Fragment() {

    private var mSpecAdapter: SpecAdapter? = null

    private var mBinding: SpecListFragmentBinding? = null

    private var mViewModel : SpecListViewModel? = null

    private val mSpecClickCallback = object : SpecClickCallback {
        override fun onClick(specItem: SpecItem) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as StartActivity).show(specItem)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater!!, R.layout.spec_list_fragment, container, false)

        mSpecAdapter = SpecAdapter(mSpecClickCallback)
        mBinding?.specItemList?.adapter = mSpecAdapter

        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        mViewModel = ViewModelProviders.of(this).get(SpecListViewModel::class.java)
                .apply {
                    FastTraceApp.getInstance().applicationComponent.inject(this)
                    subscribeUi(this) }
    }

    private fun subscribeUi(viewModel: SpecListViewModel) {
        // Update the list when the data changes
        viewModel.getSpecListItems(arguments.getLong(KEY_FAST_TRACE_ENTITY)).observe(this, Observer{updateSpecList(it)})
        mBinding?.viewModel = viewModel
    }

    private fun updateSpecList(specList : List<SpecItem>?) {
        Log.d(TAG, "updateSpecList: size = " + specList?.size)
        if (specList != null) {
            mBinding!!.isLoading = false
            mSpecAdapter!!.setSpecItemList(specList)
        } else {
            mBinding!!.isLoading = true
        }
        // espresso does not know how to wait for data binding's loop so we execute changes
        // sync.
        mBinding!!.executePendingBindings()
    }


    companion object {
        val KEY_FAST_TRACE_ENTITY : String = "FT_ENTITY"
        private val TAG = "SpecListFragment"

        fun forFastTraceEntity(fastTraceEntity : Long): SpecListFragment {
            val fragment = SpecListFragment()
            val args = Bundle()
            args.putLong(KEY_FAST_TRACE_ENTITY, fastTraceEntity)
            fragment.arguments = args
            return fragment
        }
    }
}
