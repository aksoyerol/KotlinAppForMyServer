package com.erolaksoy.androidkotlinappformybackend.ui.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class LoginViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val fragmentList = ArrayList<Fragment>()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

}