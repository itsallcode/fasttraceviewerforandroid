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