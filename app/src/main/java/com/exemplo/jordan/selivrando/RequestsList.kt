package com.exemplo.jordan.selivrando

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.exemplo.jordan.selivrando.models.Doacoes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_meus_livros.*

class RequestsList : AppCompatActivity() {


    private var user = FirebaseAuth.getInstance().currentUser  //Pega a instancia do usuario logado
    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mDatabase = FirebaseDatabase.getInstance().getReference();

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        val postListener = mDatabase?.addValueEventListener( object : ValueEventListener { // Pega no banco os livros para montar a Recyclo View
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                var arrayDoacoes:ArrayList<Doacoes> = ArrayList<Doacoes>()
                var doacoes = dataSnapshot.child("doacoes").child(user?.uid.toString()).children
                doacoes.forEach {
                    var doacao: Doacoes? = it.getValue(Doacoes::class.java)
                    Log.i("Firebase", doacao?.toString())
                    Log.i("Firebase", doacao?.doador)
                    doacao?.doador = dataSnapshot.child("users").child(doacao?.doador).child("nome").getValue().toString()
                    doacao?.interessado = dataSnapshot.child("users").child(doacao?.doador).child("nome").getValue().toString()
                    arrayDoacoes.add(doacao!!)

                }
                var ea = MyAdapterRequests(this, arrayDoacoes){  //CAso clicado em algum item da Recyclo View, Acessar o ID em questão e enviar para outra Activity, Para puxar os dados!
                    var i = Intent(this@RequestsList, AcceptRequest::class.java)
                    i.putExtra("RequestId", it.id)
                    startActivity(i)
                }
                rv.adapter = ea
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
