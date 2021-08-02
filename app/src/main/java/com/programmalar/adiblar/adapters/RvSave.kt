package com.programmalar.adiblar.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.programmalar.adiblar.R
import com.programmalar.adiblar.database.AppDatabase
import com.programmalar.adiblar.databinding.ItemRvBinding
import com.programmalar.adiblar.entitiy.WriterEntity
import com.programmalar.adiblar.models.Writer
import com.squareup.picasso.Picasso

class RvSave(var context: Context,var onItemClickListener: OnItemClickListener):ListAdapter<WriterEntity,RvSave.Vh>(RvItemDiffUtil()) {
    inner class Vh(var itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        lateinit var firebaseFirestore: FirebaseFirestore
        lateinit var appDatabase: AppDatabase
        lateinit var firabaseStorage:FirebaseStorage
        fun onBind(writerEntity: WriterEntity, position: Int) {
            firebaseFirestore = FirebaseFirestore.getInstance()
            firabaseStorage = FirebaseStorage.getInstance()
            appDatabase = AppDatabase.getInstance(context)
            itemRvBinding.root.startAnimation(AnimationUtils.loadAnimation(context,R.anim.animation_item))
                firebaseFirestore.collection(writerEntity.category!!)
                    .document("${writerEntity.name}").get()
                    .addOnCompleteListener { value ->
                        if (value.isSuccessful) {
                            val writer = value.result!!.toObject(Writer::class.java)
                            Picasso.get().load(writer!!.image).into(itemRvBinding.imageWriter)
                            itemRvBinding.nameWriter.text = writer.info
                            itemRvBinding.cons.setBackgroundColor(Color.parseColor("#00B238"))
                            itemRvBinding.imageSave.setImageResource(writerEntity.image!!)
                        }
            }

            itemRvBinding.card.setOnClickListener {
                firebaseFirestore.collection(writerEntity.category!!)
                    .document("${writerEntity.name}").get()
                    .addOnCompleteListener { value ->
                        if (value.isSuccessful) {
                            val writer = value.result!!.toObject(Writer::class.java)
                            onItemClickListener.onItemClick(writer!!,position,writerEntity)
                        }
                    }
            }

            itemRvBinding.save.setOnClickListener {
                writerEntity.image = R.drawable.ic_frame__4_
                appDatabase.addDao().updateWriter(writerEntity)
            }
        }
    }



         class RvItemDiffUtil : DiffUtil.ItemCallback<WriterEntity>() {

            override fun areItemsTheSame(oldItem: WriterEntity, newItem: WriterEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: WriterEntity, newItem: WriterEntity): Boolean {
                return oldItem.equals(newItem)
            }

        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position), position)
    }
    interface OnItemClickListener{
        fun onItemClick(writer: Writer,position: Int,writerEntity: WriterEntity)
    }

}


