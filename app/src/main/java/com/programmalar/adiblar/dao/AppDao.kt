package com.programmalar.adiblar.dao

import androidx.room.*
import com.programmalar.adiblar.entitiy.WriterEntity
import io.reactivex.Flowable

@Dao
interface AppDao {
    @Insert
    fun addWriter(writerEntity: WriterEntity)

    @Update
    fun updateWriter(writerEntity: WriterEntity)

    @Delete
    fun deleteWriter(writerEntity: WriterEntity)

    @Query("select*from writerentity where writer_name=:name")
    fun getWriterById(name:String):WriterEntity

    @Query("select*from writerentity where writer_name=:name")
    fun getWriterById1(name:String):Flowable<WriterEntity>

    @Query("select*from writerentity")
    fun getWriterAll():List<WriterEntity>

    @Query("select*from writerentity where image_category=:image")
    fun getWriterAll2(image:Int):List<WriterEntity>

    @Query("select*from writerentity where image_category=:image")
    fun getWriterAll1(image:Int):Flowable<List<WriterEntity>>
}