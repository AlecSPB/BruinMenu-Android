package com.vanshgandhi.bruinfood.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vanshgandhi.bruinfood.MainActivity
import com.vanshgandhi.bruinfood.R
import com.vanshgandhi.bruinfood.Refreshable
import com.vanshgandhi.bruinfood.adapters.HallSectionPagerAdapter

/**
 * Created by Vansh Gandhi on 12/17/16.
 * Copyright © 2016
 */

class MenuContainerFragment : Fragment(), Refreshable {
    var tabLayout: TabLayout? = null

    companion object {
        fun newInstance(): MenuContainerFragment {
            return MenuContainerFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_menu_container, container, false)
        val viewPager = view?.findViewById(R.id.hall_menu_container) as ViewPager
        val sectionsPagerAdapter = HallSectionPagerAdapter(childFragmentManager)
        tabLayout = (activity as MainActivity).tabLayout
        viewPager.offscreenPageLimit = 6
        viewPager.adapter = sectionsPagerAdapter
        tabLayout?.setupWithViewPager(viewPager)
        return view
    }

    override fun onRefresh() {
        return
    }
}