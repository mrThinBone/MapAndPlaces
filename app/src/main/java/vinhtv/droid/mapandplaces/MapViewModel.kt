package vinhtv.droid.mapandplaces

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.schedulers.Schedulers
import vinhtv.droid.mapandplaces.mediator.OperationWrapper
import vinhtv.droid.mapandplaces.model.MapPlace
import vinhtv.droid.mapandplaces.usecase.FindPlaces

class MapViewModel(private val findPlacesTask: FindPlaces) : ViewModel() {

    private val liveMapPlaces = MutableLiveData<OperationWrapper<List<MapPlace>>>()

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