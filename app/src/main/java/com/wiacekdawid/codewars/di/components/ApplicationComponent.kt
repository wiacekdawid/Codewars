package com.wiacekdawid.codewars.di.components

import com.wiacekdawid.codewars.CodewarsApplication
import com.wiacekdawid.codewars.di.modules.ActivityBuilder
import com.wiacekdawid.codewars.di.modules.ApplicationModule
import com.wiacekdawid.codewars.di.modules.NetModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, ApplicationModule::class, NetModule::class, ActivityBuilder::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent
        @BindsInstance
        fun codewarsApplication(codewarsApplication: CodewarsApplication): Builder
    }

    fun inject(codewarsApplication: CodewarsApplication)
}