package com.wiacekdawid.codewars

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.wiacekdawid.codewars.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dawidwiacek on 28/04/2018.
 */


class CodewarsApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    init {
        initDI()
        initLogger()
    }
    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        initStetho()
        initDI()
        initLogger()
    }

    private fun initStetho() = Stetho.initializeWithDefaults(this)

    private fun initDI() = DaggerApplicationComponent
                .builder()
                .codewarsApplication(this)
                .build()
                .inject(this)

    private fun initLogger() = Timber.plant(Timber.DebugTree())

}