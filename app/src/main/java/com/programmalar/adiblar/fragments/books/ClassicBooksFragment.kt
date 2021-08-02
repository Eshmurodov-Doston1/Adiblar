  package com.programmalar.adiblar.fragments.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.programmalar.adiblar.R
import com.programmalar.adiblar.adapters.RvAdapterWriter
import com.programmalar.adiblar.database.AppDatabase
import com.programmalar.adiblar.databinding.FragmentClassicBooksBinding
import com.programmalar.adiblar.databinding.ItemRvBinding
import com.programmalar.adiblar.entitiy.WriterEntity
import com.programmalar.adiblar.models.Writer


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClassicBooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClassicBooksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var fragmentClassicBooksBinding: FragmentClassicBooksBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var root:View
    lateinit var appDatabase: AppDatabase

    lateinit var rvAdapterWriter:RvAdapterWriter
    lateinit var listWriter:ArrayList<Writer>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentClassicBooksBinding = FragmentClassicBooksBinding.inflate(inflater,container,false)
        root = fragmentClassicBooksBinding.root
        firebaseFirestore = FirebaseFirestore.getInstance()
        listWriter = ArrayList()
        appDatabase = AppDatabase.getInstance(root.context)


        firebaseFirestore.collection("$param2").addSnapshotListener { value, error ->
            val result = value
            result!!.documentChanges.forEach {queryDocumentSnapshot->
                when(queryDocumentSnapshot.type){
                  DocumentChange.Type.ADDED->{
                      var writer = queryDocumentSnapshot.document.toObject(Writer::class.java)
                      listWriter.add(writer)
                  }

//                    DocumentChange.Type.REMOVED->{
//                        listWriter.clear()
//                        var writer = queryDocumentSnapshot.document.toObject(Writer::class.java)
//                        listWriter.add(writer)
//                    }
                }
            }
            rvAdapterWriter = RvAdapterWriter(root.context,listWriter,object:RvAdapterWriter.OnItemClickListener{
                override fun onItemClick(writer: Writer, position: Int,writerEntity: WriterEntity) {
                    var bundle = Bundle()
                    bundle.putSerializable("writer",writer)
                    bundle.putSerializable("writerEntity",writerEntity)
                    var navOptions = NavOptions.Builder()
                    navOptions.setEnterAnim(R.anim.enter)
                    navOptions.setExitAnim(R.anim.exite)
                    navOptions.setPopEnterAnim(R.anim.pop_ente)
                    navOptions.setPopExitAnim(R.anim.pop_exite)
                    findNavController().navigate(R.id.informationFragment,bundle,navOptions.build())
                }
            })
            fragmentClassicBooksBinding.writersRv.adapter = rvAdapterWriter
        }


        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClassicBooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int,param2: String) =
            ClassicBooksFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}