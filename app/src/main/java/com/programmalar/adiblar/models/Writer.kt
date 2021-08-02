package com.programmalar.adiblar.models

import java.io.Serializable

class Writer:Serializable{
    var info:String?=null
    var image:String?=null
    var year:String?=null
    var writerInfo:String?=null
    var category:String?=null
    var category_position:Int?=null
    constructor()
    constructor(
        info: String?,
        image: String?,
        year: String?,
        writerInfo: String?,
        category: String?,
        category_position:Int?
    ) {
        this.info = info
        this.image = image
        this.year = year
        this.writerInfo = writerInfo
        this.category = category
        this.category_position = category_position
    }
}