package vinhtv.droid.mapandplaces.datasource

import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import vinhtv.droid.mapandplaces.App
import vinhtv.droid.mapandplaces.datasource.converter.PlaceConverterFactory

class ApiFactory {

    companion object {

        private val retrofit = mapRetrofit()
        val placeApi: PlaceService = retrofit.create(PlaceService::class.java)

        private fun mapRetrofit(): Retrofit {
            // 5mb
            val cacheSize = (5 * 1024 * 1024).toLong()
            val httpClient = OkHttpClient.Builder()
                .cache(Cache(App.getInstance().cacheDir, cacheSize))
                .addInterceptor{ chain ->
                    var request = chain.request()
                    //cache for a day
                    request = request.newBuilder().header("Cache-Control", "public, max-age=86400").build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(PlaceConverterFactory(Gson()))
                .client(httpClient)
                .build()
        }
    }
}