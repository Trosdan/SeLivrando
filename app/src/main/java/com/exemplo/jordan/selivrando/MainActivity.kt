package com.exemplo.jordan.selivrando

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.exemplo.jordan.selivrando.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (user != null) {
            Toast.makeText(this@MainActivity, "Usuario Logado " + user.email, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@MainActivity, "Nenhum usuario logado", Toast.LENGTH_LONG).show()
        }


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        var eventos:ArrayList<Evento> = ArrayList<Evento>()
        for(i in 0..100){
            var e = Evento("Livro: ${i}", "Descrição: ${i}", "Autor: ${i}")
            eventos.add(e)
        }
        var ea = MyAdapter(this, eventos){
            //var i = Intent(Intent.ACTION_VIEW, Uri.parse("http:/www.google.com"))
            var i = Intent(this@MainActivity, BookdescActivity::class.java)
            i.putExtra("teste", "iphone")
            i.putExtra("teste2", "ios")
            startActivity(i)
        }
        rv.adapter = ea
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        userEmail.text = user?.email  //Muda usuario
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_manage -> {

            }
            R.id.nav_logout ->{
                FirebaseAuth.getInstance().signOut()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    public fun btn_FirstBook(view: View){
        startActivity(Intent(this@MainActivity, BookdescActivity::class.java))
    }

    fun qualquermerdaai(){


    }
}
