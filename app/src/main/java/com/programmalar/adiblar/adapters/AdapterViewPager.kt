package com.programmalar.adiblar.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.programmalar.adiblar.fragments.books.ClassicBooksFragment
import com.programmalar.adiblar.models.Category

class AdapterViewPager(var list: List<Category>,fragmentActivity:FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return ClassicBooksFragment.newInstance(list[position].position!!, list[position].name!!)
    }
}