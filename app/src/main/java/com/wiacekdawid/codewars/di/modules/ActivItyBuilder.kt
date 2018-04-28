package com.wiacekdawid.codewars.di.modules

import android.app.Activity
import com.wiacekdawid.codewars.di.components.CodewarsActivitySubcomponent
import com.wiacekdawid.codewars.ui.CodewarsActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Module
abstract class ActivityBuilder {
    @Binds
    @IntoMap
    @ActivityKey(CodewarsActivity::class)
    internal abstract fun bindCodewarsActivity(builder: CodewarsActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>
}