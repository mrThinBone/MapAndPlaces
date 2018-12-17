package vinhtv.droid.mapandplaces.datasource.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import vinhtv.droid.mapandplaces.datasource.exception.AppApiException
import java.io.IOException

//https://hackernoon.com/retrofit-converter-for-wrapped-responses-8919298a549c
class PlaceResponseBodyConverter<T>(private val converter: Converter<ResponseBody, PlaceResponseBody<T>>) :
    Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): T? {
        try {
            val response = converter.convert(responseBody)!!
            if (response.status == "OK") {
                return if (response.detail != null) response.detail!! else response.places
            } else throw AppApiException(response.status, response.message)
        } finally {
            responseBody.close()
        }
    }
}