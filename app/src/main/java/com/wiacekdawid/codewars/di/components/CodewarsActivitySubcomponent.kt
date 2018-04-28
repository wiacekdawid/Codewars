package com.wiacekdawid.codewars.di.components

import com.wiacekdawid.codewars.di.modules.CodewarsActivityModule
import com.wiacekdawid.codewars.di.modules.FragmentBuilder
import com.wiacekdawid.codewars.di.scopes.ActivityScope
import com.wiacekdawid.codewars.ui.CodewarsActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@ActivityScope
@Subcomponent(modules = arrayOf(CodewarsActivityModule::class, FragmentBuilder::class))
interface CodewarsActivitySubcomponent : AndroidInjector<CodewarsActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CodewarsActivity>()
}