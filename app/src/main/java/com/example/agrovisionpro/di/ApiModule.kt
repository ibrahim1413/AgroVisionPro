package com.example.agrovisionpro.di

import com.example.agrovisionpro.BuildConfig
import com.example.agrovisionpro.api.APIs
import com.example.agrovisionpro.api.ApiService
import com.example.agrovisionpro.repository.WeatherRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides

    fun provideBaseUrl(): HttpUrl {
        return API_URL
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val mBuilder = OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)


        // Logging Interceptor=============================
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        return mBuilder
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides

    fun provideRetrofit( mBaseUrl: HttpUrl, mClient: OkHttpClient, mMoshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(mClient)
            .baseUrl(mBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(mMoshi))
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideAPIService( mRetrofit: Retrofit): ApiService {
        return mRetrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        apiService: ApiService
    ): WeatherRepository {
        return WeatherRepository(apiService)
    }


    companion object {
        val API_URL: HttpUrl = APIs.BASE_URL.toHttpUrl()
    }
}