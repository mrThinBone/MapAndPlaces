package vinhtv.droid.mapandplaces.datasource

import com.google.android.gms.maps.model.LatLng
import vinhtv.droid.mapandplaces.datasource.local.PlacesLocalDatasource
import vinhtv.droid.mapandplaces.datasource.remote.PlacesRemoteDatasource
import vinhtv.droid.mapandplaces.model.MapPlace

class PlacesRepositoryIml(private val localDataSource: PlacesLocalDatasource, private val remoteDataSource: PlacesRemoteDatasource): PlacesRepository {

    override fun findPlaces(latLng: LatLng): List<MapPlace> {
        val localSource = localDataSource.findPlaces(latLng)
        if(localSource.isNotEmpty()) return localSource

        val dataSource = remoteDataSource.findPlaces(latLng)
        localDataSource.cache(dataSource)
        return dataSource
    }

}