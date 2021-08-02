package com.programmalar.adiblar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.programmalar.adiblar.R
import com.programmalar.adiblar.adapters.ViewPagerAdapter
import com.programmalar.adiblar.databinding.FragmentMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
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
    lateinit var fragmentMainBinding:FragmentMainBinding
    lateinit var root:View
    lateinit var viewPagerAdapter:ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      fragmentMainBinding = FragmentMainBinding.inflate(inflater,container,false)
        root = fragmentMainBinding.root
        setUpViewPager()

        fragmentMainBinding.bottomNavigation.onItemSelected = {
         when(it){
             0->{
                 fragmentMainBinding.viewPager.currentItem=0
             }
             1->{
                 fragmentMainBinding.viewPager.currentItem=1
             }
             2->{
                 fragmentMainBinding.viewPager.currentItem=2
             }
         }
        }
        return root
    }


    fun setUpViewPager(){
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        fragmentMainBinding.viewPager.adapter = viewPagerAdapter
        fragmentMainBinding.viewPager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0->{
                        fragmentMainBinding.bottomNavigation.itemActiveIndex = 0

                    }
                    1->{
                        fragmentMainBinding.bottomNavigation.itemActiveIndex = 1
                    }
                    2->{
                        fragmentMainBinding.bottomNavigation.itemActiveIndex = 2
                    }
                }
            }

        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}