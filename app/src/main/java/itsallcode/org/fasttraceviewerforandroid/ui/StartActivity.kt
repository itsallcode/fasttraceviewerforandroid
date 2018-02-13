package itsallcode.org.fasttraceviewerforandroid.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import itsallcode.org.fasttraceviewerforandroid.R
import itsallcode.org.fasttraceviewerforandroid.model.FastTraceItem
import itsallcode.org.fasttraceviewerforandroid.model.SpecItem
import itsallcode.org.fasttraceviewerforandroid.ui.fasttraceentities.FastTraceListFragment
import itsallcode.org.fasttraceviewerforandroid.ui.speclist.SpecListFragment
import openfasttrack.core.LinkedSpecificationItem

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

    /** Shows the product detail fragment  */
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

    fun show(item: LinkedSpecificationItem) {
        //
        //        ProductFragment productFragment = ProductFragment.forProduct(product.getId());
        //
        //        getSupportFragmentManager()
        //                .beginTransaction()
        //                .addToBackStack("product")
        //                .replace(R.id.fragment_container,
        //                        productFragment, null).commit();
    }
}
