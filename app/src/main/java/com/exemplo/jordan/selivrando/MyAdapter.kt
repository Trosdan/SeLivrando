package com.exemplo.jordan.selivrando

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.itemlist.view.*

/**
 * Created by eqs on 20/03/2018.
 */
class MyAdapter (val context: Context, val eventos:ArrayList<Evento>, val clickListener:(Evento)->Unit):
        RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.itemlist, parent, false)
        var vh = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder:ViewHolder , position: Int) {
        holder.itemView.tvNome.text = eventos[position].nome
        holder.itemView.tvuUerPublic.text = eventos[position].desc

        holder.itemView.setOnClickListener{clickListener(eventos[position])}
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

}