package com.wiacekdawid.codewars.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wiacekdawid.codewars.BuildConfig
import com.wiacekdawid.codewars.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Module
class NetModule {

    private val TIMEOUT = 15L

    @Provides
    @ApplicationScope
    internal fun provideGson() = GsonBuilder().create()

    @Provides
    @ApplicationScope
    internal fun provideOkHttpClient() = OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()

    @Provides
    @ApplicationScope
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

}