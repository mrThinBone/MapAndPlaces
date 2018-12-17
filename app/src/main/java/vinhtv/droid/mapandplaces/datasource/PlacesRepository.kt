package vinhtv.droid.mapandplaces.datasource

import com.google.android.gms.maps.model.LatLng
import vinhtv.droid.mapandplaces.model.MapPlace

interface PlacesRepository {
    fun findPlaces(latLng: LatLng): List<MapPlace>
}