package com.vanshgandhi.bruinfood.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import com.vanshgandhi.bruinfood.MainActivity
import com.vanshgandhi.bruinfood.R
import com.vanshgandhi.bruinfood.Refreshable
import org.joda.time.Days
import org.joda.time.LocalDate

/**
 * Swipes reset Monday morning
 *
 * Created by Vansh Gandhi on 12/17/16.
 * Copyright © 2016
 */

class SwipesFragment : Fragment(), Refreshable {
    //TODO: Figure out how to use Kotterknife in here

    val MEAL_PLAN_KEY = "MEAL_PLAN"

    val TOTAL_19P_SWIPES = 228 // 19 * 12 (0 week + 10 weeks + finals week)
    val TOTAL_14P_SWIPES = 168 // 14 * 12 (0 week + 10 weeks + finals week)
    val SWIPES_19: IntArray = intArrayOf(16, 13, 10, 7, 4, 2, 0) //3 per weekday. 2 per weekend day
    val SWIPES_14: IntArray = intArrayOf(12, 10, 8, 6, 4, 2, 0) //2 per day
    val SWIPES_11: IntArray = intArrayOf(9, 7, 5, 3, 1, 1, 0) //2 per weekday, 1 for entire weekend

    lateinit var mealSelectorRadioGroup: RadioGroup
    lateinit var swipeCountTextView: TextView

    val quarterStart = LocalDate.now().withMonthOfYear(1).withDayOfMonth(9).withYear(2017)!!
    lateinit var keyIdMap: List<Pair<String, Int>>

    lateinit var preferences: SharedPreferences

    companion object {
        fun newInstance(): SwipesFragment {
            return SwipesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_swipes, container, false)
        mealSelectorRadioGroup = view?.findViewById(R.id.meal_selector) as RadioGroup
        swipeCountTextView = view?.findViewById(R.id.swipe_count_textview) as TextView
        val P19 = Pair("P19", R.id.toggle_19p)
        val P14 = Pair("P14", R.id.toggle_14p)
        val R19 = Pair("R19", R.id.toggle_19)
        val R14 = Pair("R14", R.id.toggle_14)
        val R11 = Pair("R11", R.id.toggle_11)
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        keyIdMap = listOf(P19, P14, R19, R14, R11)
        mealSelectorRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            onRefresh()
            val key = getSharedPreferencesKeyFromMealPlanId(checkedId)
            preferences.edit().putString(MEAL_PLAN_KEY, key).apply()
        }
        mealSelectorRadioGroup.check(getMealPlanIdFromSharedPreferencesKey())
        return view
    }

    private fun getSwipes(checkedId: Int): String {
        when (checkedId) {
            R.id.toggle_11 -> return getRegularSwipes(SWIPES_11)
            R.id.toggle_14 -> return getRegularSwipes(SWIPES_14)
            R.id.toggle_19 -> return getRegularSwipes(SWIPES_19)
            R.id.toggle_14p -> return get14PSwipes()
            R.id.toggle_19p -> return get19PSwipes()
        }
        return ""
    }

    private fun getRegularSwipes(swipesArray: IntArray) : String {
        val day = getSelectedDate().dayOfWeek
        return swipesArray[day - 1].toString()
    }

    private fun get14PSwipes(): String {
        //2 per day
        val daysSinceQuarter = Days.daysBetween(quarterStart, getSelectedDate())
        val numSwipesLeft = TOTAL_14P_SWIPES - (daysSinceQuarter.days * 2)
        return numSwipesLeft.toString()
    }

    private fun get19PSwipes(): String {
        //3 per weekday. 2 per weekend day
        val daysSinceQuarter = Days.daysBetween(quarterStart, getSelectedDate())
        val numSwipesLeft = TOTAL_19P_SWIPES - (daysSinceQuarter.days * 2)
        return "TODO"
    }

    private fun getSelectedDate(): LocalDate {
        return (activity as MainActivity).selectedDate
    }

    private fun getMealPlanIdFromSharedPreferencesKey(): Int {
        val plan = preferences.getString(MEAL_PLAN_KEY, keyIdMap[0].first)
        keyIdMap
                .filter { it.first == plan }
                .forEach { return it.second }
        return -1
    }

    private fun getSharedPreferencesKeyFromMealPlanId(checkedId: Int): String {
        keyIdMap
                .filter { it.second == checkedId }
                .forEach { return it.first }
        return ""
    }

    override fun onRefresh() {
        swipeCountTextView.text = getSwipes(mealSelectorRadioGroup.checkedRadioButtonId)
    }
}