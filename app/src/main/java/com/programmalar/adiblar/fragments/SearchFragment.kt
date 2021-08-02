package com.programmalar.adiblar.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentContainer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.programmalar.adiblar.R
import com.programmalar.adiblar.adapters.AdapterSearch
import com.programmalar.adiblar.database.AppDatabase
import com.programmalar.adiblar.databinding.FragmentSearchBinding
import com.programmalar.adiblar.entitiy.WriterEntity
import com.programmalar.adiblar.models.Category
import com.programmalar.adiblar.models.Writer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
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
    lateinit var fragmentSearchBinding: FragmentSearchBinding
    lateinit var root:View
    lateinit var adapterSearch: AdapterSearch
    lateinit var listCategory:ArrayList<Category>
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var appDatabase: AppDatabase
    lateinit var listWriterSearch:ArrayList<Writer>
    lateinit var listWriter:ArrayList<Writer>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       fragmentSearchBinding = FragmentSearchBinding.inflate(inflater,container,false)
        root = fragmentSearchBinding.root
        loadCategory()
        appDatabase= AppDatabase.getInstance(root.context)
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        listWriter = ArrayList()
        for (category in listCategory) {
            firebaseFirestore.collection(category.name!!).addSnapshotListener { value, error ->
                value!!.documentChanges.forEach {
                    when(it.type){
                        DocumentChange.Type.ADDED->{
                            var writer = it.document.toObject(Writer::class.java)
                            listWriter.add(writer)
                        }
                    }
                }
                adapterSearch = AdapterSearch(root.context,listWriter,object:AdapterSearch.OnItemClickListener{
                    override fun onItemClick(
                        writer: Writer,
                        position: Int,
                        writerEntity: WriterEntity
                    ) {
                        var bundle = Bundle()
                        bundle.putSerializable("writer",writer)
                        bundle.putSerializable("writerEntity",writerEntity)
                        var navOption = NavOptions.Builder()
                        navOption.setEnterAnim(R.anim.enter)
                        navOption.setExitAnim(R.anim.exite)
                        navOption.setPopEnterAnim(R.anim.pop_ente)
                        navOption.setPopExitAnim(R.anim.pop_exite)
                        hideKeyboard(requireActivity())
                        findNavController().navigate(R.id.informationFragment,bundle,navOption.build())

                    }
                })
                fragmentSearchBinding.rvSearch.adapter = adapterSearch
            }
        }

        fragmentSearchBinding.searchInfo.addTextChangedListener {
                if (it!!.isNotEmpty()) {
                    filter1(it.toString().trim())
                } else{
                    listWriterSearch.clear()
                    listWriterSearch.addAll(listWriter)
                }
        }

        fragmentSearchBinding.clouse.setOnClickListener {
            val toString = fragmentSearchBinding.searchInfo.text.toString()
            adapterSearch.notifyDataSetChanged()
            if (toString.isNotEmpty()){
                fragmentSearchBinding.searchInfo.setText("")
            }else{
                findNavController().popBackStack()
            }
        }


        return root
    }

    private fun filter1(text: String) {
       listWriterSearch = ArrayList<Writer>()
        for (writer in listWriter) {
            if (writer.info!!.toLowerCase().contains(text.toLowerCase())){
                listWriterSearch.add(writer)
            }
        }
        adapterSearch.filterList(listWriterSearch)
//        if (listWriterSearch==null) {
//            adapterSearch.filterList(listWriterSearch1)
//        }else{
//
//        }
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
    private fun loadCategory() {
        listCategory  = ArrayList()
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
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}