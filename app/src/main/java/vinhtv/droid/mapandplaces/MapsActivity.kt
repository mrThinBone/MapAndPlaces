package vinhtv.droid.mapandplaces

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.viewmodel.ext.android.viewModel
import vinhtv.droid.mapandplaces.mediator.Indicator
import vinhtv.droid.mapandplaces.model.MapPlace

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mAdapter: MapRestaurantAdapter
    private lateinit var mScrollDisposable: Disposable
    private val mCenter = LatLng(10.7899659, 106.6361499)

    private val mViewModel: MapViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mAdapter = MapRestaurantAdapter()
        rclMapPlaces.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rclMapPlaces.adapter = mAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rclMapPlaces)

        val scrollObservable = ScrollListener.scrollStateChangeObservable(rclMapPlaces)
        mScrollDisposable = scrollObservable.subscribe {
            Log.d("scroll", "current selected: $it")
            val place = mAdapter.getItem(it)
            place?.let {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 15.0f))
            }
        }

        mViewModel.getMapPlaces().observe(this, Observer {
            it?.let { operation ->
                when(operation.indicator) {
                    Indicator.LOADING -> Log.d("getting_data", "loading")
                    Indicator.FAILED -> Log.d("getting_data", it.reason())
                    Indicator.SUCCESS -> loadPlaces(it.data)
                }
            }
        })
        if(savedInstanceState == null) {
            mViewModel.findPlaces(mCenter)
        }
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
        mMap.uiSettings.isZoomGesturesEnabled = false
//        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val pos: Int = marker.tag as Int
        rclMapPlaces.scrollToPosition(pos)
        // true: disable auto map focus on this marker
        return true
    }

    private fun loadPlaces(data: List<MapPlace>?) {
        data?.let {
            mAdapter.load(it)
            drawMarkers(it)
        }
    }

    private fun drawMarkers(data: List<MapPlace>) {
        for((i, place) in data.withIndex()) {
            val marker = mMap.addMarker(MarkerOptions()
                .position(place.latLng!!))
            marker.tag = i
        }
        if(data.isNotEmpty()) mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(data[0].latLng, 15.0f))
    }

}
