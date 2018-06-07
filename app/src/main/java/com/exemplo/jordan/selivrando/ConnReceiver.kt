package com.exemplo.jordan.selivrando

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

/**
 * Created by Gabriel M on 07/06/2018.
 */
class ConnReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var cm:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var netInfo = cm.activeNetworkInfo

        if(netInfo != null && netInfo.isConnectedOrConnecting){
            Toast.makeText(context, "Conectado com a Internet ", Toast.LENGTH_LONG).show()
            Log.i("BRTESTE", "INTERNET ON")


        }else{
            Toast.makeText(context, "Internet Desconectada ", Toast.LENGTH_LONG).show()
            Log.i("BRTESTE", "INTERNET OFF")
        }
    }


}