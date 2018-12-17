package vinhtv.droid.mapandplaces.datasource.remote

import com.google.android.gms.maps.model.LatLng
import vinhtv.droid.mapandplaces.datasource.PlaceService
import vinhtv.droid.mapandplaces.datasource.PlacesDataSource
import vinhtv.droid.mapandplaces.model.MapPlace

class PlacesRemoteDatasource(private val placeService: PlaceService): PlacesDataSource {

    override fun findPlaces(latLng: LatLng): List<MapPlace> {
        val location = "${latLng.latitude},${latLng.longitude}"
        val response = placeService.findPlaces(location = location, radius = "10000").execute()
        if(response.isSuccessful) {
            return response.body()?.places?: throw Exception("findPlaces API: something's wrong!!!")
        } else throw Exception(response.message())
    }

    override fun cache(data: List<MapPlace>) {
        // not supported
    }

}