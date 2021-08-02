package com.programmalar.adiblar.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.programmalar.adiblar.fragments.SaveFragment
import com.programmalar.adiblar.fragments.SettingsFragment
import com.programmalar.adiblar.fragments.WritersLifeFragment

class   ViewPagerAdapter(fragmentManager: FragmentManager):FragmentStatePagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                WritersLifeFragment()
            }
            1->{
                SaveFragment()
            }
            2->{
                SettingsFragment()
            }
            else->{
                WritersLifeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}