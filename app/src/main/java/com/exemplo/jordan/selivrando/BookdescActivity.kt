package com.exemplo.jordan.selivrando

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.exemplo.jordan.selivrando.models.Livro
import com.exemplo.jordan.selivrando.models.Usuario
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_bookdesc2.*

class BookdescActivity : AppCompatActivity() {


    private var mDatabase: DatabaseReference? = null

    public var endereco = ""
    public var livroId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookdesc2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mDatabase = FirebaseDatabase.getInstance().getReference();
        val getUsuario = mDatabase?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                var idLivro = intent.extras.get("livroId").toString()       // Tras o dado passado da Activity anterior
                var livro = dataSnapshot.child("livros").child(idLivro).getValue(Livro::class.java) //traz o livro pelo ID

                livroTitulo.text = livro?.titulo                                    //Adiciona os dados
                livroGenero.text = "Genero: ${livro?.genero.toString()}"
                livroAno.text = "Ano: ${livro?.ano}"
                livroEdicao.text = "Edição: ${livro?.edicao}º Edição"
                livroPaginas.text = "Paginas: ${livro?.paginas}"
                livroProprietario.text = "Publicado: ${dataSnapshot.child("users").child(livro?.proprietario).child("nome").getValue().toString()}"
                proprietarioEndereco.text = "Endereço: ${dataSnapshot.child("users").child(livro?.proprietario).child("endereço").getValue().toString()}"
                endereco = dataSnapshot.child("users").child(livro?.proprietario).child("endereço").getValue().toString()
                livroISBN.text = "ISBN: ${livro?.isbn}"
                livroDescricao.text = "Descrição: \n${livro?.descricao}"
                livroId = livro?.id_livro.toString()

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

    }

    public fun btnVerMapa(view: View) {
        var i = Intent(this@BookdescActivity, MapsActivity::class.java)
        i.putExtra("endereco", endereco)
        startActivity(i)
    }

    public fun btnFazerPedido(view: View) {
        var i = Intent(this@BookdescActivity, MainActivity::class.java)
        i.putExtra("livroid", livroId)
        startActivity(i)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
