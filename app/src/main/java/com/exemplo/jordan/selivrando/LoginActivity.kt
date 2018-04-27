package com.exemplo.jordan.selivrando

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.internal.FirebaseAppHelper.getUid
import com.google.firebase.auth.FirebaseUser




class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance() //Instacia do Firebase
    }

    public fun btn_Login(view: View){

        mAuth?.signInWithEmailAndPassword(login.text.toString(), password.text.toString())
            ?.addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                Log.d("EmailPassword", "signInWithEmail:onComplete:" + task.isSuccessful)

                if (!task.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Falha no Login",
                            Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Logou!",
                            Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            })
    }


}
