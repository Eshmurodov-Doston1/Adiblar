package com.programmalar.adiblar.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentContainer
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.programmalar.adiblar.R
import com.programmalar.adiblar.adapters.AdapterViewPager
import com.programmalar.adiblar.databinding.FragmentWritersLifeBinding
import com.programmalar.adiblar.databinding.ItemTabBinding
import com.programmalar.adiblar.models.Category

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WritersLifeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WritersLifeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var fragmentWritersLifeBinding: FragmentWritersLifeBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var root:View
    lateinit var adapterViewPager:AdapterViewPager
    var MYPREFERENCES="nightModePrefe"
    var KEY_ISNIGHTMODE="nightModePrefe"
    lateinit var listCategory:ArrayList<Category>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentWritersLifeBinding = FragmentWritersLifeBinding.inflate(inflater,container,false)
        root =fragmentWritersLifeBinding.root
        loadCategory()
        sharedPreferences = activity?.getSharedPreferences(MYPREFERENCES,Context.MODE_PRIVATE)!!
        adapterViewPager = AdapterViewPager(listCategory,requireActivity())
        fragmentWritersLifeBinding.wievPager.adapter = adapterViewPager
        TabLayoutMediator(fragmentWritersLifeBinding.tabLayout,fragmentWritersLifeBinding.wievPager){tab,position->
            tab.text = listCategory[position].name
        }.attach()
        setTabs()
        fragmentWritersLifeBinding.tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab!!.customView
                val bind = ItemTabBinding.bind(customView!!)
                var gradientDrawable = bind.cons.background.mutate() as GradientDrawable
                if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
                    gradientDrawable.setColor(Color.parseColor("#25303F"))
                }else{
                    gradientDrawable.setColor(Color.WHITE)
                }
                bind.textCategory.setTextColor(Color.parseColor("#7C7979"))
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab!!.customView
                val bind = ItemTabBinding.bind(customView!!)
                bind.textCategory.setTextColor(Color.WHITE)
                var gradientDrawable = bind.cons.background.mutate() as GradientDrawable
                gradientDrawable.setColor(Color.parseColor("#00B238"))
            }

        })

        fragmentWritersLifeBinding.search.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        return root
    }

    private fun setTabs() {
        val tabCount = fragmentWritersLifeBinding.tabLayout.tabCount
        for (i in 0 until tabCount){
            var itemTabBinding = ItemTabBinding.inflate(LayoutInflater.from(root.context),null,false)
            val tabAt = fragmentWritersLifeBinding.tabLayout.getTabAt(i)
            tabAt!!.customView = itemTabBinding.root
            itemTabBinding.textCategory.text = listCategory[i].name
            if (i==0){
                var gradientDrawable = itemTabBinding.cons.background.mutate() as GradientDrawable
                gradientDrawable.setColor(Color.parseColor("#00B238"))
                itemTabBinding.textCategory.setTextColor(Color.WHITE)
            }else{
                var gradientDrawable = itemTabBinding.cons.background.mutate() as GradientDrawable
                if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
                    gradientDrawable.setColor(Color.parseColor("#25303F"))
                }else{
                    gradientDrawable.setColor(Color.WHITE)
                }
                itemTabBinding.textCategory.setTextColor(Color.parseColor("#7C7979"))
            }
        }
    }

    private fun loadCategory() {
        listCategory = ArrayList()
        listCategory.add(Category("Mumtoz adabiyoti",0))
        listCategory.add(Category("O’zbek adabiyoti",1))
        listCategory.add(Category("Jahon adabiyoti",2))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WritersLifeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WritersLifeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}