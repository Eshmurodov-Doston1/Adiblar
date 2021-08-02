package com.programmalar.adiblar.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.programmalar.adiblar.R
import com.programmalar.adiblar.adapters.AdapterSearchSave
import com.programmalar.adiblar.database.AppDatabase
import com.programmalar.adiblar.databinding.FragmentSearchSaveBinding
import com.programmalar.adiblar.databinding.ItemRvBinding
import com.programmalar.adiblar.entitiy.WriterEntity
import com.programmalar.adiblar.models.Writer
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchSaveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchSaveFragment : Fragment() {
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

    lateinit var adapterSearchSave: AdapterSearchSave
    lateinit var fragmentSearchSaveBinding: FragmentSearchSaveBinding
    lateinit var appDatabase: AppDatabase
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var root: View
    lateinit var listWriterSearch:ArrayList<Writer>
    lateinit var listWriterSearch1:ArrayList<Writer>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSearchSaveBinding = FragmentSearchSaveBinding.inflate(inflater, container, false)
        root = fragmentSearchSaveBinding.root
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        appDatabase = AppDatabase.getInstance(root.context)
        val writerAll2 = appDatabase.addDao().getWriterAll2(R.drawable.ic_frame__8_)
        listWriterSearch = ArrayList()

        if(!appDatabase.isOpen){appDatabase.openHelper.writableDatabase}

        for (writerEntity in writerAll2){
            firebaseFirestore.collection(writerEntity.category!!)
                .document("${writerEntity.name}").get()
                .addOnCompleteListener { value ->
                    if (value.isSuccessful) {
                        val writer = value.result!!.toObject(Writer::class.java)
                       listWriterSearch.add(writer!!)
                        adapterSearchSave = AdapterSearchSave(root.context, listWriterSearch, object : AdapterSearchSave.OnItemClickListener {
                            override fun onItemClick(
                                writer: Writer,
                                position: Int,
                                writerEntity: WriterEntity
                            ) {

                            }
                        })
                        fragmentSearchSaveBinding.rvSearch.adapter = adapterSearchSave
                    }
                }
        }


        fragmentSearchSaveBinding.searchInfo.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                filter1(it.toString().trim())
            } else{
                listWriterSearch1.clear()
                listWriterSearch1.addAll(listWriterSearch)
            }
        }

        fragmentSearchSaveBinding.clouse.setOnClickListener {
            val toString = fragmentSearchSaveBinding.searchInfo.text.toString()
            adapterSearchSave.notifyDataSetChanged()
            if (toString.isNotEmpty()){
                fragmentSearchSaveBinding.searchInfo.setText("")
            }else{
                findNavController().popBackStack()
            }
        }
            return root
        }


    private fun filter1(text: String) {
        listWriterSearch1 = ArrayList()
        for (writer in listWriterSearch) {
            if (writer.info!!.toLowerCase().contains(text.toLowerCase())){
                listWriterSearch1.add(writer)
            }
        }
        adapterSearchSave.filterList(listWriterSearch1)
//        if (listWriterSearch==null) {
//            adapterSearch.filterList(listWriterSearch1)
//        }else{
//
//        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchSaveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchSaveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}