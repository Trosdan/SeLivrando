package com.exemplo.jordan.selivrando

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exemplo.jordan.selivrando.models.Doacoes
import com.exemplo.jordan.selivrando.models.Genero
import com.exemplo.jordan.selivrando.models.Livro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.itemlist.view.*

/**
 * Created by eqs on 07/06/2018.
 */
class MyAdapterRequests (val context: ValueEventListener, val doacoes:ArrayList<Doacoes>, val clickListener:(Doacoes)->Unit):
        RecyclerView.Adapter<MyAdapterRequests.ViewHolder>() {


    private var user = FirebaseAuth.getInstance().currentUser
    private var mDatabase: DatabaseReference? = null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterRequests.ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.itemlist, parent, false)
        var vh = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: MyAdapterRequests.ViewHolder, position: Int) {
        val addValueEventListener = mDatabase?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                holder.itemView.tvuUerPublic.text = dataSnapshot.child("livros").child(doacoes[position].livro).child("titulo").toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        holder.itemView.tvNome.text = doacoes[position].interessado.toString()

        holder.itemView.setOnClickListener{clickListener(doacoes[position])}
    }

    override fun getItemCount(): Int {
        return doacoes.size
    }
}