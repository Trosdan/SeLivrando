package com.exemplo.jordan.selivrando

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import android.widget.ProgressBar
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var latlng: LatLng

    lateinit var progress: ProgressDialog

    private var strEnd = "Rua José Gomes Varela";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener { this@MapsActivity }

        // Add a marker in Sydney and move the camera
        val jampa = LatLng(-7.158838, -34.8576049)
        mMap.addMarker(MarkerOptions().position(jampa).title("A wonderfull city"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jampa, 18.5f))
        strEnd = intent.extras.get("endereco").toString()
        goToAddress()
    }

    fun createMarkers(latLng: LatLng, title:String, snippet:String): MarkerOptions{
        return MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet)
        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
    }

    fun onMapClick(latlng:LatLng) {
        this.latlng = latlng

        progress = ProgressDialog(this)
        progress.setTitle("Carregando endereço ...")
        progress.show();

        ReserveLatLngToAddress().execute()
    }

    fun goToAddress(){

        progress = ProgressDialog(this)
        progress.setTitle("Carregando endereço ...")
        progress.show();

        //strEnd = etEnd.text.toString()

        ReserveAddressToLatLng().execute()
    }


    inner class ReserveLatLngToAddress : AsyncTask<Void, Void, Address>(){

        override fun doInBackground(vararg p0: Void?): Address? {
            try {

                var geo = Geocoder(this@MapsActivity)
                var addresses = geo.getFromLocation(this@MapsActivity.latlng.latitude
                        ,this@MapsActivity.latlng.longitude
                        , 1)

                return addresses.get(0)

            }catch (e:Exception){
                return null
            }
        }

        override fun onPostExecute(result: Address?) {

            this@MapsActivity.progress.dismiss()

            if(result != null){
                this@MapsActivity.mMap.addMarker(
                        this@MapsActivity.createMarkers(
                                this@MapsActivity.latlng,
                                result.thoroughfare,
                                result.locality + " - " + result.postalCode
                        )
                )
            }
        }
    }

    inner class ReserveAddressToLatLng : AsyncTask<Void, Void, Address>(){

        override fun doInBackground(vararg p0: Void?): Address? {
            try {

                var geo = Geocoder(this@MapsActivity)
                val addresses = geo.getFromLocationName(this@MapsActivity.strEnd, 1)
                return addresses.get(0)

            }catch (e:Exception){
                return null
            }
        }

        override fun onPostExecute(result: Address?) {

            this@MapsActivity.progress.dismiss()

            if(result != null){
                var latlng = LatLng(result.latitude, result.longitude)
                this@MapsActivity.mMap.addMarker(
                        this@MapsActivity.createMarkers(
                                latlng,
                                result.thoroughfare,
                                result.locality + " - " + result.postalCode
                        )
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18.5f))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
