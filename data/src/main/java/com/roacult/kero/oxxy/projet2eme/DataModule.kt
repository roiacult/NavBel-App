package com.roacult.kero.oxxy.projet2eme

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.projet2eme.repositories.AutherntificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class DataModule {
//
//     @Binds
//     abstract fun provideAuthentificationRepository(repositoryImpl: AutherntificationRepositoryImpl):AuthentificationRepository
//
//    @Provides
//    @Singleton
//    fun provideRetrofite():Retrofit{
//
//    }
//    @Provides
//    @Singleton
//    fun provideClient():OkHttpClient{
//        return OkHttpClient.Builder().addInterceptor
//        (HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//    }

}