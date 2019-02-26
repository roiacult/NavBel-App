package com.roacult.kero.oxxy.projet2eme.di.Module

import com.roacult.kero.oxxy.domain.functional.AppRxSchedulers
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import com.roacult.kero.oxxy.projet2eme.utils.Schedulers.AppCoroutineDispatchers
import com.roacult.kero.oxxy.projet2eme.utils.Schedulers.AppRxSchedulersImpl
import javax.inject.Singleton

@Module
class AppModule {

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