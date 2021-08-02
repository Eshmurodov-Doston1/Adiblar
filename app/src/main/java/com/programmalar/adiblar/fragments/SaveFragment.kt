package com.programmalar.adiblar.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.programmalar.adiblar.R
import com.programmalar.adiblar.adapters.RvAdapterWriter
import com.programmalar.adiblar.adapters.RvSave
import com.programmalar.adiblar.database.AppDatabase
import com.programmalar.adiblar.databinding.FragmentSaveBinding
import com.programmalar.adiblar.databinding.ItemRvBinding
import com.programmalar.adiblar.entitiy.WriterEntity
import com.programmalar.adiblar.models.Category
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.Writer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SaveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SaveFragment : Fragment() {
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
    lateinit var fragmentSaveBinding: FragmentSaveBinding
    lateinit var root:View
    lateinit var rvAdapterWriter: RvAdapterWriter
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firabaseStorage: FirebaseStorage
    lateinit var appDatabase: AppDatabase
    lateinit var listCategory:ArrayList<Category>
    lateinit var listSaveWriter:ArrayList<com.programmalar.adiblar.models.Writer>
    lateinit var listName:ArrayList<WriterEntity>
    lateinit var name:ArrayList<String>
    lateinit var saveAdapter:RvSave
    private val TAG = "SaveFragment"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       fragmentSaveBinding = FragmentSaveBinding.inflate(inflater,container,false)
        root = fragmentSaveBinding.root
        appDatabase = AppDatabase.getInstance(root.context)
        if (!appDatabase.isOpen) { appDatabase.openHelper.writableDatabase }
        fragmentSaveBinding.searchSave.setOnClickListener {
            findNavController().navigate(R.id.searchSaveFragment)
        }
        saveAdapter = RvSave(root.context,object:RvSave.OnItemClickListener{
            override fun onItemClick(
                writer: com.programmalar.adiblar.models.Writer,
                position: Int,
                writerEntity: WriterEntity
            ) {
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
        appDatabase.addDao().getWriterAll1(R.drawable.ic_frame__8_)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:Consumer<List<WriterEntity>>{
                override fun accept(t: List<WriterEntity>?) {
                 saveAdapter.submitList(t)
                }
            })
        fragmentSaveBinding.rvSave.adapter = saveAdapter

        return root
    }


    override fun onResume() {
        super.onResume()



//            firebaseFirestore.collection(writerEntity1.category!!).whereIn("info",name).addSnapshotListener  { value, error ->
//                    val result = value
//                listSaveWriter.clear()
//                   result!!.documentChanges.forEach {queryDocumentSnapshot->
//                       when(queryDocumentSnapshot.type){
//                           DocumentChange.Type.ADDED->{
//                               val writer =
//                                   queryDocumentSnapshot.document.toObject(com.programmalar.adiblar.models.Writer::class.java)
//                               listSaveWriter.add(writer)
//                           }
//                   }
//                }
//                saveAdapter = RvSave(root.context, listSaveWriter, object : RvSave.OnDeleteSave {
//                    override fun deleteSave(
//                        writerEntity: com.programmalar.adiblar.models.Writer,
//                        position: Int,
//                        itemRvBinding: ItemRvBinding
//                    ) {
//                        if (writerEntity.info!!.toLowerCase() == writerEntity1.name!!.toLowerCase()) {
//                            appDatabase.addDao().deleteWriter(writerEntity1)
//                            listSaveWriter.remove(writerEntity)
//                            saveAdapter.notifyItemRangeRemoved(position, listSaveWriter.size)
//                        }
//                    }
//
//                })
//                fragmentSaveBinding.rvSave.adapter = saveAdapter
////                    var writer =
////                        result!!.toObject(com.programmalar.adiblar.models.Writer::class.java)
////                    listSaveWriter.add(writer!!)
////                var writer = queryDocumentSnapshot.document.toObject(com.programmalar.adiblar.models.Writer::class.java)
////                            if (writer.info!!.toLowerCase() == writerEntity1.name!!.toLowerCase()) {
//
////                            }
////                result!!.documentChanges.forEach { queryDocumentSnapshot ->
////                    when (queryDocumentSnapshot.type) {
////                        DocumentChange.Type.ADDED -> {
//
////                        }
////                        DocumentChange.Type.REMOVED -> {
////                            var adib = queryDocumentSnapshot.document.toObject(com.programmalar.adiblar.models.Writer::class.java)
////                            if (adib.info!!.toLowerCase() != writerEntity.name!!.toLowerCase()) {
////                                listSaveWriter.remove(adib)
////                            }
////                        }
//                }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SaveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SaveFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}