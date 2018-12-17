package vinhtv.droid.mapandplaces.datasource.local

import com.google.android.gms.maps.model.LatLng
import vinhtv.droid.mapandplaces.datasource.PlacesDataSource
import vinhtv.droid.mapandplaces.model.MapPlace

class PlacesLocalDatasource: PlacesDataSource {

    override fun findPlaces(latLng: LatLng): List<MapPlace> {
        return if(validateSource(latLng)) getCache() else invalidResult()
    }

    override fun cache(data: List<MapPlace>) {
    }

    /**
     * if cache result is still usable
     */
    private fun validateSource(latLng: LatLng): Boolean {
        return false
    }

    /**
     * get current cache
     */
    private fun getCache(): List<MapPlace> = invalidResult()

    private fun invalidResult(): List<MapPlace> = emptyList()
}