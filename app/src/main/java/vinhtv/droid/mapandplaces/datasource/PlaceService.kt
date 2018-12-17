package vinhtv.droid.mapandplaces.datasource

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vinhtv.droid.mapandplaces.BuildConfig
import vinhtv.droid.mapandplaces.datasource.deserializer.PlaceResponseDataWrapper

//https://developers.google.com/places/web-service/search
interface PlaceService {

    @GET("/maps/api/place/nearbysearch/json?key=${BuildConfig.API_KEY}")
    fun findPlaces(@Query("type") type: String = "restaurant",
//                   @Query("keyword") input: String = "cruise",
                   @Query("radius") radius: String,
                   @Query("location") location: String): Call<PlaceResponseDataWrapper>



}