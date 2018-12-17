package vinhtv.droid.mapandplaces.datasource

import retrofit2.http.GET
import retrofit2.http.Query
import vinhtv.droid.mapandplaces.BuildConfig

interface DirectionService {

    @GET("/maps/api/directions/json?key=${BuildConfig.API_KEY}")
    fun getDirection(@Query("origin") origin: String, @Query("destination") destination: String)

}