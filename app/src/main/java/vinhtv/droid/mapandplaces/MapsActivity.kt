package vinhtv.droid.mapandplaces

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_maps.*
import vinhtv.droid.mapandplaces.mediator.Indicator
import vinhtv.droid.mapandplaces.model.MapPlace

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mAdapter: MapRestaurantAdapter
    private val mCenter = LatLng(10.7899659, 106.6361499)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/

        mAdapter = MapRestaurantAdapter()
        rclMapPlaces.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rclMapPlaces.adapter = mAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rclMapPlaces)

        val viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        viewModel.getMapPlaces().observe(this, Observer {
            it?.let { operation ->
                when(operation.indicator) {
                    Indicator.LOADING -> Log.d("vinhtv", "loading")
                    Indicator.FAILED -> Log.d("vinhtv", it.reason())
                    Indicator.SUCCESS -> loadPlaces(it.data)
                }
            }
        })
        viewModel.findPlaces(mCenter)
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
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mCenter))
    }

    private fun loadPlaces(data: List<MapPlace>?) {
        mAdapter.load(data)
    }
}
