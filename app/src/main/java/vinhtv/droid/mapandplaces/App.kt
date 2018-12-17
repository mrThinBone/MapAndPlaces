package vinhtv.droid.mapandplaces

import android.app.Application
import org.koin.android.ext.android.startKoin
import vinhtv.droid.mapandplaces.di.PlaceModule

class App: Application() {

    companion object {
        private var instance: App? = null
        fun getInstance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(PlaceModule.getAppModule()))
    }

}