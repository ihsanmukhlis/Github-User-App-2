package com.ihsanmkls.githubuserapp2.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ihsanmkls.githubuserapp2.view.ListFollowFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return ListFollowFragment.newInstance(position + 1)
    }

    override fun getItemCount(): Int {
        return 2
    }

}