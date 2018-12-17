package vinhtv.droid.mapandplaces.datasource

import com.google.android.gms.maps.model.LatLng
import vinhtv.droid.mapandplaces.model.MapPlace

class PlacesRepositoryIml(private val localDataSource: PlacesDataSource, private val remoteDataSource: PlacesDataSource): PlacesRepository {

    override fun findPlaces(latLng: LatLng): List<MapPlace> {
        val localSource = localDataSource.findPlaces(latLng)
        if(localSource.isNotEmpty()) return localSource

        val dataSource = remoteDataSource.findPlaces(latLng)
        localDataSource.cache(dataSource)
        return dataSource
    }

}