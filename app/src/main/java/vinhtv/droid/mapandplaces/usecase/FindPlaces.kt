package vinhtv.droid.mapandplaces.usecase

import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import vinhtv.droid.mapandplaces.datasource.PlacesRepository
import vinhtv.droid.mapandplaces.model.MapPlace

class FindPlaces(private val placesRepository: PlacesRepository): BaseUsecase<LatLng, Single<List<MapPlace>>> {

    override fun execute(params: LatLng): Single<List<MapPlace>> {
        return Single.create {
            try {
                val mapPlaces = placesRepository.findPlaces(latLng = params)
                it.onSuccess(mapPlaces)
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}