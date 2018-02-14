package itsallcode.org.fasttraceviewerforandroid.ui.fasttraceentities

import android.support.v4.app.Fragment
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.databinding.FastTraceListFragmentBinding
import itsallcode.org.fasttraceviewerforandroid.model.FastTraceItem
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity
import itsallcode.org.fasttraceviewerforandroid.viewmodel.FastTraceViewModel
import android.content.Intent
import android.support.design.widget.Snackbar
import android.util.Log
import itsallcode.org.fasttraceviewerforandroid.FastTraceApp
import itsallcode.org.fasttraceviewerforandroid.ui.StartActivity
import itsallcode.org.fasttraceviewerforandroid.util.setupSnackbar


/**
 * Created by thomasu on 2/4/18.
 */

class FastTraceListFragment : Fragment() {

    private var mFastTraceEntityAdapter: FastTraceEntityAdapter? = null

    private var mBinding: FastTraceListFragmentBinding? = null

    private var mViewModel : FastTraceViewModel? = null

    private val mFastTraceClickCallback = object : FastTraceClickCallback {
        override fun onClick(fastTraceItem: FastTraceItem) {
            Log.d(TAG, "onClick")
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as StartActivity).show(fastTraceItem)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fast_trace_list_fragment, container, false)

        mFastTraceEntityAdapter = FastTraceEntityAdapter(mFastTraceClickCallback)
        mBinding?.fastTraceItemList?.adapter = mFastTraceEntityAdapter

        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(FastTraceViewModel::class.java)
                .apply {
                    FastTraceApp.getInstance().applicationComponent.inject(this)
                    subscribeUi(this) }
    }

    private fun subscribeUi(viewModel: FastTraceViewModel) {
        // Update the list when the data changes
        viewModel.getFastTraceItems().observe(this, Observer{updateFastTraceEntities(it)})
        viewModel.newTaskEvent.observe(this, Observer { openFile() })
        view?.setupSnackbar(this@FastTraceListFragment,
                viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
        mBinding?.viewModel = viewModel
    }

    private fun updateFastTraceEntities(items : List<FastTraceEntity>?) {
        if (items != null) {
            mBinding!!.isLoading = false
            mFastTraceEntityAdapter!!.setFastTraceItemList(items)
        } else {
            mBinding!!.isLoading = true
        }
        // espresso does not know how to wait for data binding's loop so we execute changes
        // sync.
        mBinding!!.executePendingBindings()
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.type = "*/*"

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE) {
            data?.let {
                mViewModel?.importFile(it.data)
            }
        }
    }

    companion object {
        private const val READ_REQUEST_CODE = 42
        private const val TAG = "FastTraceFragment"
    }
}
