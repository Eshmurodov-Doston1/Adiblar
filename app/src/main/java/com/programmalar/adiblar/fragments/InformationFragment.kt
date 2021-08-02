package com.programmalar.adiblar.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.navigation.fragment.findNavController
import com.example.mydictionary.utils.MyBounceInterpolator
import com.google.android.material.appbar.AppBarLayout
import com.programmalar.adiblar.R
import com.programmalar.adiblar.database.AppDatabase
import com.programmalar.adiblar.databinding.FragmentInformationBinding
import com.programmalar.adiblar.entitiy.WriterEntity
import com.programmalar.adiblar.models.Writer
import com.programmalar.adiblar.utils.InfoClass
import com.squareup.picasso.Picasso


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InformationFragment : Fragment() {
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
    lateinit var fragmentInformationBinding: FragmentInformationBinding
    lateinit var appDatabase: AppDatabase
    lateinit var sharedPreferences: SharedPreferences
    var MYPREFERENCES="nightModePrefe"
    var KEY_ISNIGHTMODE="nightModePrefe"
    lateinit var root:View
     var toolBarState:Boolean=false
    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentInformationBinding = FragmentInformationBinding.inflate(inflater,container,false)
        root = fragmentInformationBinding.root
        val writer = arguments?.getSerializable("writer") as Writer
        val writerEntity = arguments?.getSerializable("writerEntity") as WriterEntity
        appDatabase = AppDatabase.getInstance(root.context)

        sharedPreferences = activity?.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE)!!
        if (writerEntity.image==R.drawable.ic_frame__8_){
            fragmentInformationBinding.imageSave.setImageResource(writerEntity.image!!)
            fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#00B238"))
        }else{
            fragmentInformationBinding.imageSave.setImageResource(R.drawable.ic_vector__2_)
            if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
                fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#25303F"))
            }else{
                fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            fragmentInformationBinding.save.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.save))
        }

        fragmentInformationBinding.collepsingToolbar.title = writer.info
        Picasso.get().load(writer.image).into(fragmentInformationBinding.image)
        fragmentInformationBinding.year.text = "(${writer.year})"
        fragmentInformationBinding.inforWriter.setText( writer.writerInfo)
        fragmentInformationBinding.clouse.setOnClickListener {
            findNavController().popBackStack()
        }

        fragmentInformationBinding.imageSave.setOnClickListener {
            if (appDatabase.addDao().getWriterById(writer.info!!) != null) {
                if (writerEntity.image == R.drawable.ic_frame__8_) {
                    fragmentInformationBinding.imageSave.setImageResource(R.drawable.ic_vector__2_)
                    if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
                        fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#25303F"))
                    }else{
                        fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    }
                    writerEntity.image = R.drawable.ic_frame__4_
                    val myAnim = AnimationUtils.loadAnimation(
                        fragmentInformationBinding.root.context,
                        R.anim.save_animation
                    )
                    val interpolator = MyBounceInterpolator(0.1, 20.0)
                    myAnim.interpolator = interpolator
                    fragmentInformationBinding.imageSave.startAnimation(myAnim)
                    appDatabase.addDao().updateWriter(writerEntity)
                } else {
                    fragmentInformationBinding.imageSave.setImageResource(R.drawable.ic_frame__8_)
                    fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#00B238"))
                    writerEntity.image = R.drawable.ic_frame__8_
                    val myAnim = AnimationUtils.loadAnimation(
                        fragmentInformationBinding.root.context,
                        R.anim.save_animation
                    )
                    val interpolator = MyBounceInterpolator(0.1, 20.0)
                    myAnim.interpolator = interpolator
                    fragmentInformationBinding.imageSave.startAnimation(myAnim)
                    appDatabase.addDao().updateWriter(writerEntity)
                }
            }else{
                fragmentInformationBinding.imageSave.setImageResource(R.drawable.ic_frame__8_)
                fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#00B238"))
                writerEntity.image = R.drawable.ic_frame__8_
                writerEntity.name = writer.info
                writerEntity.category = writer.category
                val myAnim = AnimationUtils.loadAnimation(
                    fragmentInformationBinding.root.context,
                    R.anim.save_animation
                )
                val interpolator = MyBounceInterpolator(0.1, 20.0)
                myAnim.interpolator = interpolator
                fragmentInformationBinding.imageSave.startAnimation(myAnim)
                appDatabase.addDao().addWriter(writerEntity)
            }
        }


        fragmentInformationBinding.clouseSearch.setOnClickListener {
            var search = fragmentInformationBinding.search.text.toString()
            if (search.isNotEmpty()){
                fragmentInformationBinding.search.setText("")
            }else {
                hideKeyboard(requireActivity())
                fragmentInformationBinding.toolbar.visibility = View.VISIBLE
                fragmentInformationBinding.saveToolbar.visibility = View.VISIBLE
                fragmentInformationBinding.searchToolbar.visibility = View.INVISIBLE
            }
        }


        fragmentInformationBinding.search.addTextChangedListener {
            if (it!!.length>=2) {
                fragmentInformationBinding.inforWriter.setTextToHighlight(it.toString())
                fragmentInformationBinding.inforWriter.setCaseInsensitive(true)
                if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
                    fragmentInformationBinding.inforWriter.setTextHighlightColor("#E11010")
                }else{
                    fragmentInformationBinding.inforWriter.setTextHighlightColor("#FFEB3B")
                }

                fragmentInformationBinding.inforWriter.highlight()
            }else{
                fragmentInformationBinding.inforWriter.setTextToHighlight(it.toString())
                fragmentInformationBinding.inforWriter.setCaseInsensitive(true)
                if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
                    fragmentInformationBinding.inforWriter.setTextHighlightColor("#25303F")
                }else{
                    fragmentInformationBinding.inforWriter.setTextHighlightColor("#FFFFFF")
                }

                fragmentInformationBinding.inforWriter.highlight()
            }
        }


        fragmentInformationBinding.appbar.addOnOffsetChangedListener(object : InfoClass() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when (state) {
                    State.COLLAPSED -> {
                        if (writerEntity.image==R.drawable.ic_frame__8_) {
                            fragmentInformationBinding.imageSave.setImageResource(R.drawable.frame_9)
                            if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
                                fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#25303F"))
                            }else{
                                fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#FFFFFF"))
                            }
//                            fragmentInformationBinding.save.setBackgroundColor(
//                                ContextCompat.getColor(
//                                    requireContext(),
//                                    R.color.white
//                                )
//                            )
                        }
//                        if (fragmentInformationBinding.searchToolbar.visibility == View.VISIBLE){
//                            fragmentInformationBinding.collepsingToolbar.title=""
//                        }
                        fragmentInformationBinding.searchIcon.setOnClickListener {
                            fragmentInformationBinding.toolbar.visibility = View.INVISIBLE
                            fragmentInformationBinding.saveToolbar.visibility = View.INVISIBLE
                            fragmentInformationBinding.searchToolbar.visibility = View.VISIBLE
                            fragmentInformationBinding.appbar.setExpanded(true,true)
                        }
                        toolBarState = false
                    }
                    State.EXPANDED -> { 
                        if (writerEntity.image==R.drawable.ic_frame__8_) {
                            fragmentInformationBinding.imageSave.setImageResource(R.drawable.ic_frame__8_)
                            if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
                                fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#00B238"))
                            }else{
                                fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#00B238"))
                            }
//                            fragmentInformationBinding.save.setBackgroundColor(
//                                ContextCompat.getColor(
//                                    requireContext(),
//                                   R.color.purple_500
//                                )
//                            )
                        }
//                        if (fragmentInformationBinding.searchToolbar.visibility == View.VISIBLE){
//                            fragmentInformationBinding.collepsingToolbar.title=writer.info
//                        }
                        fragmentInformationBinding.searchIcon.setOnClickListener {
                            fragmentInformationBinding.toolbar.visibility = View.INVISIBLE
                            fragmentInformationBinding.saveToolbar.visibility = View.INVISIBLE
                            fragmentInformationBinding.searchToolbar.visibility = View.VISIBLE
                        }

                        toolBarState = true
                    }
                    else -> {
                        if (!toolBarState) {
                            toolBarState = true
                            if (writerEntity.image==R.drawable.ic_frame__4_) {
                                fragmentInformationBinding.imageSave.setImageResource(R.drawable.ic_vector__2_)
                                if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
                                    fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#25303F"))
                                }else{
                                    fragmentInformationBinding.save.setBackgroundColor(Color.parseColor("#FFFFFF"))
                                }
//                                fragmentInformationBinding.save.setBackgroundColor(
//                                    ContextCompat.getColor(
//                                        requireContext(),
//                                        R.color.white
//                                    )
//                                )
                            }
                            if (fragmentInformationBinding.searchToolbar.visibility == View.VISIBLE){
                                fragmentInformationBinding.collepsingToolbar.title=writer.info
                            }
                        }else{
                             if (fragmentInformationBinding.searchToolbar.visibility == View.VISIBLE){
                                fragmentInformationBinding.collepsingToolbar.title=""
                            }
                        }
                    }
                }
            }
        })




        return root
    }
    fun hideKeyboard(activity: Activity) {
        val view =
            activity.findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                InformationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}