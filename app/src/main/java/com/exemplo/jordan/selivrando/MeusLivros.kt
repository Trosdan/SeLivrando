package com.exemplo.jordan.selivrando

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.exemplo.jordan.selivrando.models.Livro
import com.exemplo.jordan.selivrando.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_meus_livros.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MeusLivros : AppCompatActivity() {


    private var user = FirebaseAuth.getInstance().currentUser  //Pega a instancia do usuario logado
    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_livros)


        mDatabase = FirebaseDatabase.getInstance().getReference();

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        val postListener = mDatabase?.addValueEventListener( object : ValueEventListener { // Pega no banco os livros para montar a Recyclo View
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                var arrayLivros:ArrayList<Livro> = ArrayList<Livro>()
                var livros = dataSnapshot.child("livros").children
                livros.forEach {
                    if(it.child("proprietario").getValue().toString() == user?.uid.toString()){
                    var livro: Livro? = it.getValue(Livro::class.java)
                    livro?.proprietario = dataSnapshot.child("users").child(livro?.proprietario).child("nome").getValue().toString()
                    arrayLivros.add(livro!!)
                }

                }
                var ea = MyAdapter(this, arrayLivros){  //CAso clicado em algum item da Recyclo View, Acessar o ID em questão e enviar para outra Activity, Para puxar os dados!
                    var i = Intent(this@MeusLivros, BookdescActivity::class.java)
                    i.putExtra("livroId", it.id_livro)
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

    public override fun onStart() {
        super.onStart()
        mAuth?.addAuthStateListener(mAuthListener!!)
    }

    public override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth?.removeAuthStateListener(mAuthListener!!)
        }
    }
}
