package com.vanshgandhi.bruinfood.adapters

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter
import com.vanshgandhi.bruinfood.Models
import com.vanshgandhi.bruinfood.R
import com.vanshgandhi.bruinfood.databinding.FoodDetailBinding


/**
 * Created by Vansh Gandhi on 12/24/16.
 * Copyright © 2016
 */
class MenuAdapter(val menu: Models.Menu) : SectionedRecyclerViewAdapter<MenuAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, section: Int, relativePosition: Int, absolutePosition: Int) {
        holder?.food = menu.sections[section].foodItems[relativePosition]
        holder?.textView?.text = holder?.food?.name
    }

    override fun getItemCount(section: Int): Int {
        return menu.sections[section].foodItems.size
    }

    override fun onBindHeaderViewHolder(holder: ViewHolder?, section: Int) {
        holder?.textView?.text = menu.sections[section].name
    }

    override fun getSectionCount(): Int {
        return menu.sections.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val isHeader = viewType == VIEW_TYPE_HEADER
        val id = if (isHeader) android.R.layout.simple_list_item_1 else R.layout.food_list_item
        return ViewHolder(LayoutInflater.from(parent.context).inflate(id, parent, false), isHeader)
    }

    class ViewHolder(itemView: View?, isHeader: Boolean) : RecyclerView.ViewHolder(itemView) {
        val id = if (isHeader) android.R.id.text1 else R.id.food_item_name
        val textView = itemView?.findViewById(id) as TextView
        var food: Models.Food? = null
        init {
            itemView?.setOnClickListener  (if (isHeader) null else View.OnClickListener { v ->
                val binding = FoodDetailBinding.inflate(LayoutInflater.from(v.context))
                binding.food = food
                AlertDialog.Builder(v.context)
                        .setView(binding.root)
                        .setPositiveButton("Done", null)
                        .setTitle(food?.name)
                        .show()
            })
        }
    }
}