package com.programmalar.adiblar.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class WriterEntity:Serializable {
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
    @ColumnInfo(name="writer_name")
    var name:String?=null
    @ColumnInfo(name="writer_category")
    var category:String?=null
    @ColumnInfo(name="image_category")
    var image:Int?=null
}