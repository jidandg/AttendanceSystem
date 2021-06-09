package com.marwinjidopi.attendancesystem.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.marwinjidopi.attendancesystem.R
import com.marwinjidopi.attendancesystem.ui.home.HomeFragment
import com.marwinjidopi.attendancesystem.ui.profile.ProfileFragment

private val TAB_TITLES = arrayOf(
    R.string.title_home,
    R.string.title_profile
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return if (position == 0) HomeFragment() else ProfileFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}
