package itsallcode.org.fasttraceviewerforandroid.ui.specdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import openfasttrack.core.LinkStatus

class SpecDependencyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(i: Int): Fragment {
        val fragment = SpecDependencyListFragment()
        val args = Bundle()
        args.putInt(SpecDependencyListFragment.ARG_LINK_STATUS, LinkStatus.values()[i].ordinal)
        fragment.arguments = args
        return fragment
    }

    override fun getCount(): Int {
        return LinkStatus.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return LinkStatus.values()[position].name
    }
}