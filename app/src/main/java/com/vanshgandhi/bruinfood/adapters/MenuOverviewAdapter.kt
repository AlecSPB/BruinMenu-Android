package com.vanshgandhi.bruinfood.adapters

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter
import com.vanshgandhi.bruinfood.Models

/**
 * Created by Vansh Gandhi on 12/24/16.
 * Copyright © 2016
 */
class MenuOverviewAdapter(var menuOverview: Models.MenuOverview) : SectionedRecyclerViewAdapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, section: Int, relativePosition: Int, absolutePosition: Int) {
        val recyclerView = (holder as? MenuViewHolder)?.recyclerView
        val adapter = MenuAdapter(menuOverview.menus[section])
        adapter.setLayoutManager(recyclerView?.layoutManager as? GridLayoutManager)
        recyclerView?.adapter = adapter
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?, section: Int) {
        (holder as? HeaderViewHolder)?.textView?.text = menuOverview.menus[section].name
    }

    override fun getItemCount(section: Int): Int {
        return 1
    }

    override fun getSectionCount(): Int {
        return menuOverview.menus.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == VIEW_TYPE_HEADER) {
            return HeaderViewHolder(hallHeaderTextView(parent?.context))
        } else if (viewType == VIEW_TYPE_ITEM) {
            val recyclerView = RecyclerView(parent?.context)
            recyclerView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
            recyclerView.layoutManager = GridLayoutManager(parent?.context, 2)
            return MenuViewHolder(recyclerView)
        }
        return null
    }

    fun hallHeaderTextView(context: Context?) : TextView {
        val textView = TextView(context)
        textView.textSize = 18f // sp
        textView.setTypeface(null, Typeface.BOLD)
        return textView
    }

    class MenuViewHolder(var recyclerView: RecyclerView?) : RecyclerView.ViewHolder(recyclerView)

    class HeaderViewHolder(var textView: TextView?) : RecyclerView.ViewHolder(textView)
}