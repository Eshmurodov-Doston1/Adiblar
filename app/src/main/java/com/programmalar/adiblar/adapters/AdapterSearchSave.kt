package com.programmalar.adiblar.adapters

import android.content.Context
import android.graphics.Color

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils

import androidx.recyclerview.widget.RecyclerView
import com.example.mydictionary.utils.MyBounceInterpolator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.programmalar.adiblar.R

import com.programmalar.adiblar.database.AppDatabase
import com.programmalar.adiblar.databinding.ItemRvBinding
import com.programmalar.adiblar.entitiy.WriterEntity
import com.programmalar.adiblar.models.Writer
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class AdapterSearchSave(var context: Context,var list: List<Writer>,var onItemClickListener: AdapterSearchSave.OnItemClickListener): RecyclerView.Adapter<AdapterSearchSave.Vh>() {
    inner class Vh(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        lateinit var firebaseFirestore: FirebaseFirestore
        lateinit var appDatabase: AppDatabase
        lateinit var firabaseStorage: FirebaseStorage
        fun onBind(writer: Writer,position: Int){
            firebaseFirestore = FirebaseFirestore.getInstance()
            firabaseStorage = FirebaseStorage.getInstance()
            appDatabase = AppDatabase.getInstance(context)
            Picasso.get().load(writer.image).into(itemRvBinding.imageWriter)
            itemRvBinding.nameWriter.text = writer.info
            appDatabase = AppDatabase.getInstance(context)
            itemRvBinding.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.animation_item))

            itemRvBinding.card.setOnClickListener {
                val writerById = appDatabase.addDao().getWriterById(writer.info!!)
                if (writerById!=null) {
                    onItemClickListener.onItemClick(writer, position, writerById)
                }else{
                    onItemClickListener.onItemClick(writer, position, WriterEntity())
                }
            }

            if (writer.info!=null) {
                appDatabase.addDao().getWriterById1(writer.info!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Consumer<WriterEntity> {
                        override fun accept(t: WriterEntity?) {
                            if (t!!.image == R.drawable.ic_frame__8_) {
                                itemRvBinding.imageSave.setImageResource(t!!.image!!)
                                itemRvBinding.cons.setBackgroundColor(Color.parseColor("#00B238"))
                            }
                        }
                    })
            }


            itemRvBinding.save.setOnClickListener {
                val writerApp = appDatabase.addDao().getWriterById(writer.info!!)
                if (writerApp!=null){
                    if (writerApp.image== R.drawable.ic_frame__8_){
                        writerApp.image = R.drawable.ic_frame__4_
                        itemRvBinding.imageSave.setImageResource(writerApp.image!!)
                        itemRvBinding.cons.setBackgroundColor(Color.parseColor("#F9F9F9"))
                        val myAnim = AnimationUtils.loadAnimation(itemRvBinding.root.context, R.anim.save_animation)
                        val interpolator = MyBounceInterpolator(0.1, 20.0)
                        myAnim.interpolator = interpolator
                        itemRvBinding.imageSave.startAnimation(myAnim)
                        appDatabase.addDao().updateWriter(writerApp)
                    }else if (writerApp.image== R.drawable.ic_frame__4_){
                        writerApp.image = R.drawable.ic_frame__8_
                        itemRvBinding.imageSave.setImageResource(writerApp.image!!)
                        val myAnim = AnimationUtils.loadAnimation(itemRvBinding.root.context, R.anim.save_animation)
                        val interpolator = MyBounceInterpolator(0.1, 20.0)
                        myAnim.interpolator = interpolator
                        itemRvBinding.imageSave.startAnimation(myAnim)
                        itemRvBinding.cons.setBackgroundColor(Color.parseColor("#00B238"))
                        appDatabase.addDao().updateWriter(writerApp)
                    }
                }else{
                    val writerEntity = WriterEntity()
                    writerEntity.image= R.drawable.ic_frame__8_
                    writerEntity.name = writer.info
                    writerEntity.category = writer.category
                    itemRvBinding.imageSave.setImageResource(writerEntity.image!!)
                    val myAnim = AnimationUtils.loadAnimation(itemRvBinding.root.context, R.anim.save_animation)
                    val interpolator = MyBounceInterpolator(0.1, 20.0)
                    myAnim.interpolator = interpolator
                    itemRvBinding.imageSave.startAnimation(myAnim)
                    itemRvBinding.cons.setBackgroundColor(Color.parseColor("#00B238"))
                    appDatabase.addDao().addWriter(writerEntity)
                }
            }
            itemRvBinding.year.text = "${writer.year}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    interface OnItemClickListener{
        fun onItemClick(writer: Writer,position: Int,writerEntity: WriterEntity)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    fun filterList(filterList:ArrayList<Writer>){
        list = filterList
        notifyDataSetChanged()
    }
}