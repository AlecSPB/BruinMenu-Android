package com.vanshgandhi.bruinfood.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vanshgandhi.bruinfood.BruinFoodApplication
import com.vanshgandhi.bruinfood.MainActivity
import com.vanshgandhi.bruinfood.R
import com.vanshgandhi.bruinfood.Refreshable
import com.vanshgandhi.bruinfood.adapters.MenuAdapter
import com.vanshgandhi.bruinfood.adapters.MenuOverviewAdapter
import org.joda.time.LocalDate
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Vansh Gandhi on 12/23/16.
 * Copyright © 2016
 */
class MenuFragment : Fragment(), Refreshable, SwipeRefreshLayout.OnRefreshListener {

    lateinit var swipeRefresh: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView

    var hallCode: Int = -1

    companion object {
        val HALL_CODE_KEY = "position"
        fun newInstance(hallCode: Int): MenuFragment {
            val fragment = MenuFragment()
            val bundle = Bundle()
            bundle.putInt(HALL_CODE_KEY, hallCode)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_menu, container, false)
        if (arguments != null) {
            hallCode = arguments.getInt(HALL_CODE_KEY, -1)
        }
        swipeRefresh = view?.findViewById(R.id.swipe_refresh) as SwipeRefreshLayout
        recyclerView = view?.findViewById(R.id.recycler_view) as RecyclerView
        swipeRefresh.setOnRefreshListener(this)
//        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        onRefresh()
        return view
    }

    override fun onRefresh() {
        //TODO
        if (hallCode < 0) {
            BruinFoodApplication.menuApi.getMenuOverview(getSelectedDate())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ menuOverview ->
                        recyclerView.adapter = MenuOverviewAdapter(menuOverview)
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        swipeRefresh.isRefreshing = false
                    }, { e ->
                        Log.e("MenuFragment", "getOverview error", e)
                        //TODO: Display error message, w/ retry button
                        swipeRefresh.isRefreshing = false
                    })
        } else {
            BruinFoodApplication.menuApi.getFullMenu(hallCode, getSelectedDate())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ menu ->
                        val adapter = MenuAdapter(menu)
                        val gridLayoutManger = GridLayoutManager(context, 2)
                        adapter.setLayoutManager(gridLayoutManger)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = gridLayoutManger
                        swipeRefresh.isRefreshing = false
                    }, { e ->
                        Log.e("MenuFragment", "getFullMenu error", e)
                        //TODO: Display error message, w/ retry button
                        swipeRefresh.isRefreshing = false
                    })
        }
    }

    fun getSelectedDate(): LocalDate? {
        return (activity as? MainActivity)?.selectedDate
    }
}