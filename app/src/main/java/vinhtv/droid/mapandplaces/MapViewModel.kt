package vinhtv.droid.mapandplaces

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.reactivex.schedulers.Schedulers
import vinhtv.droid.mapandplaces.datasource.ApiFactory
import vinhtv.droid.mapandplaces.datasource.PlacesRepositoryIml
import vinhtv.droid.mapandplaces.datasource.local.PlacesLocalDatasource
import vinhtv.droid.mapandplaces.datasource.remote.PlacesRemoteDatasource
import vinhtv.droid.mapandplaces.mediator.OperationWrapper
import vinhtv.droid.mapandplaces.model.MapPlace
import vinhtv.droid.mapandplaces.usecase.FindPlaces

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val liveMapPlaces = MutableLiveData<OperationWrapper<List<MapPlace>>>()
    private val findPlacesTask: FindPlaces

    init {
        val localPlacesResource = PlacesLocalDatasource()
        val remotePlacesResource = PlacesRemoteDatasource(ApiFactory.placeApi)
        val placesRepository = PlacesRepositoryIml(localPlacesResource, remotePlacesResource)
        findPlacesTask = FindPlaces(placesRepository)
    }

    fun getMapPlaces() = liveMapPlaces

    @SuppressLint("CheckResult")
    fun findPlaces(latLng: LatLng) {
        findPlacesTask.execute(latLng)
            .doOnSubscribe {
                liveMapPlaces.postValue(OperationWrapper.stateLoad())
            }
            .subscribeOn(Schedulers.io())
            .subscribe({
                liveMapPlaces.postValue(OperationWrapper.success(it))
            }, {
                liveMapPlaces.postValue(OperationWrapper.failed(it as Exception))
            })
    }

}