package vinhtv.droid.mapandplaces.datasource

import com.google.android.gms.maps.model.LatLng
import vinhtv.droid.mapandplaces.model.MapPlace

interface PlacesDataSource {

    fun findPlaces(latLng: LatLng): List<MapPlace>

    fun cache(data: List<MapPlace>)
}