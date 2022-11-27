package com.lasadu.videos_sharing.application

import android.app.Application
import com.lasadu.videos_sharing.de.AppDependencyInjection
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(AppDependencyInjection.appModule)
        }
    }
}