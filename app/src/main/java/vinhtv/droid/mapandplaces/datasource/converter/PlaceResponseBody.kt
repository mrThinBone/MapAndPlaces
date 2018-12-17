package vinhtv.droid.mapandplaces.datasource.converter

import com.google.gson.annotations.SerializedName

data class PlaceResponseBody<T>(
    @SerializedName("status")
    val status: String = "OK",
    @SerializedName("result")
    var detail: T?,
    @SerializedName("results")
    var places: T?,
    @SerializedName("error_message")
    var message: String?)