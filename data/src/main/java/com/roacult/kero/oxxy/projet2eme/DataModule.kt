package com.roacult.kero.oxxy.projet2eme

import com.roacult.kero.oxxy.data.BuildConfig
import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.projet2eme.network.services.AuthentificationService
import com.roacult.kero.oxxy.projet2eme.repositories.AutherntificationRepositoryImpl
import com.roacult.kero.oxxy.projet2eme.utils.BASE_URL
import com.roacult.kero.oxxy.projet2eme.utils.UserAgentInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
abstract class DataModule {

     @Binds
     @Singleton
     abstract fun provideAuthentificationRepository(repositoryImpl: AutherntificationRepositoryImpl):AuthentificationRepository

    @Provides
    @Singleton
    fun provideAutherntificationService(retrofit: Retrofit):AuthentificationService{
        return retrofit.create(AuthentificationService::class.java)
    }
    @Provides
    @Singleton
    fun provideRetrofite(client :OkHttpClient):Retrofit{
      return  Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
          .addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL).client(client).build()
    }
    @Provides
    @Singleton
    fun provideClient():OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)).addInterceptor(UserAgentInterceptor(
            "navbell", BuildConfig.VERSION_NAME
        )).build()
    }


}