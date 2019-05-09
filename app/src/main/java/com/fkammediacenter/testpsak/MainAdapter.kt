package com.fkammediacenter.testpsak

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fkammediacenter.testpsak.base.BaseDiffAdapter
import com.fkammediacenter.testpsak.base.VIEW_TYPE_NORMAL
import com.fkammediacenter.testpsak.daoResponse.DonaturKotakList
import kotlinx.android.synthetic.main.item_donatur.view.*

class MainAdapter(var listener : ItemClickListener): BaseDiffAdapter<DonaturKotakList?, RecyclerView.ViewHolder>(){

    interface ItemClickListener{
        fun onItemClicked(donatur: DonaturKotakList?)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NORMAL)
            MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_donatur, parent, false))
        else LoadingViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_loading, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL){
            var donatur = getItem(position)
            var viewHolder = holder as MainViewHolder

            viewHolder.nama_outlet.text = donatur?.namaOutlet
            viewHolder.nama_pemilik.text = donatur?.namaPemilik
            viewHolder.alamat.text = donatur?.alamatOutlet
            viewHolder.itemView.setOnClickListener ({ v -> listener.onItemClicked(donatur!!) })

        }
    }


    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nama_outlet  = view.tv_nama_toko
        val nama_pemilik = view.tv_nama_pemilik
        val alamat = view.tv_alamat
    }

}