package com.vanshgandhi.bruinfood.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.vanshgandhi.bruinfood.fragments.MenuFragment

/**
 * Created by Vansh Gandhi on 12/17/16.
 * Copyright © 2016
 */

class HallSectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        //Map from position to hall code
        var hallCode = -1 //Default (when position is 0)
        when (position) {
            1 -> hallCode = 1 //TODO: Find out the actual hall code
            2 -> hallCode = 2
            3 -> hallCode = 3
            4 -> hallCode = 4
        }
        return MenuFragment.newInstance(hallCode)
    }

    override fun getCount(): Int {
        return 5 // Show 5 tabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "All"
            1 -> return "Covel"
            2 -> return "De Neve"
            3 -> return "FEAST"
            4 -> return "BPlate"
        }
        return null
    }
}