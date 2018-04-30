package com.wiacekdawid.codewars.di.modules

import android.arch.persistence.room.Room
import com.wiacekdawid.codewars.CodewarsApplication
import com.wiacekdawid.codewars.data.local.LocalDataSource
import com.wiacekdawid.codewars.data.remote.RemoteDataSource
import com.wiacekdawid.codewars.data.remote.api.CodewarsService
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.di.components.CodewarsActivitySubcomponent
import com.wiacekdawid.codewars.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Module(subcomponents = [(CodewarsActivitySubcomponent::class)])
class ApplicationModule {

    @ApplicationScope
    @Provides
    internal fun provideCodewarsService(retrofit: Retrofit) = retrofit.create(CodewarsService::class.java)

    @ApplicationScope
    @Provides
    internal fun provideRemoteDataSource(codewarsService: CodewarsService) = RemoteDataSource(codewarsService)

   /*@ApplicationScope
    @Provides
    internal fun provideLocalDataSource(codewarsApplication: CodewarsApplication): LocalDataSource =*/


    @ApplicationScope
    @Provides
    internal fun provideCodewarsRepository(codewarsApplication: CodewarsApplication,
                                           remoteDataSource: RemoteDataSource) =
        CodewarsRepository(remoteDataSource, Room.databaseBuilder(codewarsApplication,
                LocalDataSource::class.java, "codewars.db")
                .build())

}