package com.vanshgandhi.bruinfood

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.bindView
import com.roughike.bottombar.BottomBar
import com.vanshgandhi.bruinfood.fragments.HoursFragment
import com.vanshgandhi.bruinfood.fragments.MenuContainerFragment
import com.vanshgandhi.bruinfood.fragments.SwipesFragment
import org.joda.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val TAB_VISIBILITY_KEY: String = "tab_visibility"
    val bottomBar: BottomBar by bindView(R.id.bottom_bar)
    val tabLayout: TabLayout by bindView(R.id.tabs)
    var selectedDate: LocalDate = LocalDate.now()!!
    val minDate = selectedDate.minusDays(3)!!
    val maxDate = selectedDate.plusDays(7)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        updateToolbarTitle()
        //TODO: Figure out how to hide this bottom bar, or use roughike's library

        bottomBar.setOnTabSelectListener { tabId ->
            tabLayout.visibility = if (tabId == R.id.halls) View.VISIBLE else View.GONE
            when(tabId) {
                R.id.halls -> launchHallsFragment()
                R.id.swipes -> launchSwipesFragment()
                R.id.hours -> launchHoursFragment()
            }
        }
    }

    private fun launchHallsFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, MenuContainerFragment.newInstance(), "HALL_MENUS_FRAG").commit()
    }

    private fun launchSwipesFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, SwipesFragment.newInstance(), "SWIPES_FRAG").commit()
    }

    private fun launchHoursFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, HoursFragment.newInstance(), "HOURS_FRAG").commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MainActivity", String.format("selected year: %d, month: %d, day: %d", selectedDate.year, selectedDate.monthOfYear, selectedDate.dayOfMonth))
        val id = item.itemId
        if (id == R.id.action_select_date) {
            val datePickerDialog = DatePickerDialog(this, { datePicker, year, month, day ->
                selectedDate = selectedDate.withDayOfMonth(day).withMonthOfYear(month + 1).withYear(year) //returned month is Jan=0, Joda is Jan=1
                Log.d("MainActivity", String.format("new selectedyear: %d, month: %d, day: %d", year, month, day))
                updateToolbarTitle()
                supportFragmentManager.fragments
                        .filterIsInstance<Refreshable>()
                        .forEach { it.onRefresh() }
                //TODO: Use Rx
            }, selectedDate.year, selectedDate.monthOfYear - 1, selectedDate.dayOfMonth)
            datePickerDialog.datePicker.minDate = minDate.toDate().time
            datePickerDialog.datePicker.maxDate = maxDate.toDate().time
            datePickerDialog.show()
            return true
        } else if (id == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            //TODO: Settings screen
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            tabLayout.visibility = savedInstanceState.getInt(TAB_VISIBILITY_KEY, View.VISIBLE)
        } else {
            launchHallsFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(TAB_VISIBILITY_KEY, tabLayout.visibility)
    }

    private fun updateToolbarTitle() {
        supportActionBar?.title = selectedDate.toString("MMM dd")
    }
}
