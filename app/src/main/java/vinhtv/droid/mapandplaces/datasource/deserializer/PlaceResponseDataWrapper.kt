package vinhtv.droid.mapandplaces.datasource.deserializer

import com.google.gson.annotations.JsonAdapter
import vinhtv.droid.mapandplaces.model.MapPlace

@JsonAdapter(PlaceDeserializer::class)
data class PlaceResponseDataWrapper(val places: List<MapPlace>)