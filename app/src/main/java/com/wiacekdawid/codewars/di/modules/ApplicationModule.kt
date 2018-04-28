package com.wiacekdawid.codewars.di.modules

import android.arch.persistence.room.Room
import com.wiacekdawid.codewars.CodewarsApplication
import com.wiacekdawid.codewars.data.local.DbDataSource
import com.wiacekdawid.codewars.di.components.CodewarsActivitySubcomponent
import com.wiacekdawid.codewars.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Module(subcomponents = [(CodewarsActivitySubcomponent::class)])
class ApplicationModule {
    @Provides
    @ApplicationScope
    internal fun provideMainDatabase(codewarsApplication: CodewarsApplication) = Room.databaseBuilder(codewarsApplication,
            DbDataSource::class.java, "codewars.db")
            .build()
}