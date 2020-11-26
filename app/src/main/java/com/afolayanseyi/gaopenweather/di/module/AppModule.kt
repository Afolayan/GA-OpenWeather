package com.afolayanseyi.gaopenweather.di.module

import android.app.Application
import com.afolayanseyi.gaopenweather.BuildConfig
import com.afolayanseyi.gaopenweather.R
import com.afolayanseyi.gaopenweather.network.HttpLogger
import com.afolayanseyi.gaopenweather.util.AppScheduler
import com.afolayanseyi.gaopenweather.util.SchedulerInterface
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Reusable
    fun provideAppScheduler(): SchedulerInterface = AppScheduler()

    @Provides
    fun createClient(
        httpLogger: HttpLogger
    ): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor(httpLogger)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Reusable
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLogger = HttpLogger()

    @Provides
    @Reusable
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofit(
        application: Application,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(application.getString(R.string.base_url))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}