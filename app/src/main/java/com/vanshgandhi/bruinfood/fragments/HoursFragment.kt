package com.vanshgandhi.bruinfood.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minimize.android.rxrecycleradapter.RxDataSource
import com.vanshgandhi.bruinfood.BruinFoodApplication
import com.vanshgandhi.bruinfood.Models
import com.vanshgandhi.bruinfood.R
import com.vanshgandhi.bruinfood.Refreshable
import com.vanshgandhi.bruinfood.databinding.ListItemHoursBinding
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*


/**
 * Created by Vansh Gandhi on 12/17/16.
 * Copyright © 2016
 */

class HoursFragment : Fragment(), Refreshable, SwipeRefreshLayout.OnRefreshListener {

    val SAVED_HOURS_KEY = "saved_hours"
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var hours: ArrayList<Models.Hours> = arrayListOf()

    companion object {
        fun newInstance(): HoursFragment {
            return HoursFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_hours, container, false)
        swipeRefreshLayout = view?.findViewById(R.id.swipe_refresh) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        recyclerView = view?.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_HOURS_KEY)) {
            hours = savedInstanceState.getParcelableArrayList<Models.Hours>(SAVED_HOURS_KEY)
            populateRecyclerView()
        } else {
            onRefresh()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SAVED_HOURS_KEY, hours)

    }

    override fun onRefresh() {
        //TODO: Refresh animation like Relay
        swipeRefreshLayout.isRefreshing = true
        BruinFoodApplication.menuApi.getHours()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ h ->
                    hours.clear()
                    hours.addAll(h)
                    populateRecyclerView()
                    swipeRefreshLayout.isRefreshing = false
                }, { e ->
                    Log.e("HoursFragment", "getHours error", e)
                    swipeRefreshLayout.isRefreshing = false
                    //TODO: Show error message in UI w/ Try Again button
                })
    }

    private fun populateRecyclerView() {
        RxDataSource(hours).bindRecyclerView<ListItemHoursBinding>(recyclerView, R.layout.list_item_hours)
                .subscribe { viewHolder ->
                    //TODO: Make a quick icon on the card to show whether the hall is currently open
                    val binding = viewHolder.viewDataBinding
                    binding.hallName.text = viewHolder.item.hallName
                    binding.breakfastHours.text = String.format(getString(R.string.breakfast_hours), viewHolder.item.breakfast)
                    binding.lunchHours.text = String.format(getString(R.string.lunch_hours), viewHolder.item.lunch)
                    binding.dinnerHours.text = String.format(getString(R.string.dinner_hours), viewHolder.item.dinner)
                    binding.lateNightHours.text = String.format(getString(R.string.late_night_hours), viewHolder.item.lateNight)
                }
    }
}
