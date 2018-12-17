package vinhtv.droid.mapandplaces.di

import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import vinhtv.droid.mapandplaces.App
import vinhtv.droid.mapandplaces.MapViewModel
import vinhtv.droid.mapandplaces.datasource.PlaceService
import vinhtv.droid.mapandplaces.datasource.PlacesRepository
import vinhtv.droid.mapandplaces.datasource.PlacesRepositoryIml
import vinhtv.droid.mapandplaces.datasource.converter.PlaceConverterFactory
import vinhtv.droid.mapandplaces.datasource.local.PlacesLocalDatasource
import vinhtv.droid.mapandplaces.datasource.remote.PlacesRemoteDatasource
import vinhtv.droid.mapandplaces.usecase.FindPlaces

object PlaceModule {
    fun getAppModule() = module {

        single { mapRetrofit() }

        single { placeApi(get()) }

        factory { PlacesLocalDatasource() }

        factory { PlacesRemoteDatasource(get()) }

        single<PlacesRepository> { PlacesRepositoryIml(get(), get()) }

        factory { FindPlaces(get()) }

        viewModel { MapViewModel(get()) }

    }

    private fun placeApi(retrofit: Retrofit): PlaceService {
        return retrofit.create(PlaceService::class.java)
    }

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

