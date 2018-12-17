package vinhtv.droid.mapandplaces.model

import com.google.android.gms.maps.model.LatLng

data class MapPlace(val placeId: String, val id: String? = null, val name: String? = null, val latLng: LatLng? = null,
                    val photoUrl: String? = null, var rating: Float? = null, val status: String? = null,
                    var address: String? = null, var phone: String? = null, var contentIncrement: Int = 0)