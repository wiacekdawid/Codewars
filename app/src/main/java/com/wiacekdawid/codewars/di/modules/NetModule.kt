package com.wiacekdawid.codewars.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wiacekdawid.codewars.BuildConfig
import com.wiacekdawid.codewars.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Module
class NetModule {

    private val TIME_OUT = 15L

    @Provides
    @ApplicationScope
    internal fun provideGson() = GsonBuilder().create()

    @Provides
    @ApplicationScope
    @Named("CodewarsAuthorizationInterceptor")
    fun provideAccessKeyInterceptor(): Interceptor {
        return Interceptor {
            val original = it.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("access_key", BuildConfig.ACCESS_KEY)
                    .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()
            it.proceed(request)
        }
    }

    @Provides
    @ApplicationScope
    internal fun provideOkHttpClient(@Named("CodewarsAuthorizationInterceptor")interceptor: Interceptor) = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
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