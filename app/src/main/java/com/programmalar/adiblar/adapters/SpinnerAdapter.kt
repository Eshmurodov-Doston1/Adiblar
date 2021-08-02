package com.programmalar.adiblar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.programmalar.adiblar.databinding.ItemSpinnerBinding
import com.programmalar.adiblar.models.Category

class  SpinnerAdapter(var list: List<Category>):BaseAdapter() {
    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
    return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var appHelper:AppHelper
        if (convertView==null){
            var itemSpinnerBinding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent!!.context),parent,false)
            appHelper = AppHelper(itemSpinnerBinding)
        }else{
            appHelper = AppHelper(ItemSpinnerBinding.bind(convertView))
        }
        appHelper.itemSpinnerBinding.textSpinner.text = list[position].name
        return appHelper.itemView
    }

    inner class AppHelper{
        var itemView:View
        var itemSpinnerBinding:ItemSpinnerBinding
        constructor(itemSpinnerBinding: ItemSpinnerBinding) {
            itemView = itemSpinnerBinding.root
            this.itemSpinnerBinding = itemSpinnerBinding
        }
    }
}