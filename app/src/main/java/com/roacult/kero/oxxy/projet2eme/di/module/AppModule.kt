package com.roacult.kero.oxxy.projet2eme.di.module

import android.content.Context
import com.roacult.kero.oxxy.domain.functional.AppRxSchedulers
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.projet2eme.AndroidApplication
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import com.roacult.kero.oxxy.projet2eme.utils.schedulers.AppCoroutineDispatchers
import com.roacult.kero.oxxy.projet2eme.utils.schedulers.AppRxSchedulersImpl
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app:AndroidApplication):Context  = app.applicationContext

    @Provides @Singleton
    fun provideSchedulers(): AppRxSchedulers {
        return AppRxSchedulersImpl(
            Schedulers.io()
            , AndroidSchedulers.mainThread(), Schedulers.computation())
    }
    @Provides @Singleton
    fun provideDispatchers(): CouroutineDispatchers {
        return AppCoroutineDispatchers(Dispatchers.IO, Dispatchers.Unconfined, Dispatchers.Main)
    }


}