package com.wiacekdawid.codewars.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import android.net.ConnectivityManager
import com.wiacekdawid.codewars.CodewarsApplication
import com.wiacekdawid.codewars.data.local.CodewarsDatabase
import com.wiacekdawid.codewars.data.local.LocalDataSource
import com.wiacekdawid.codewars.data.local.LocalDataSourceImp
import com.wiacekdawid.codewars.data.remote.RemoteDataSource
import com.wiacekdawid.codewars.data.remote.RemoteDataSourceImp
import com.wiacekdawid.codewars.data.remote.api.CodewarsService
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.data.repository.CodewarsRepositoryImp
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
    internal fun provideRemoteDataSource(codewarsService: CodewarsService): RemoteDataSource = RemoteDataSourceImp(codewarsService)

    @ApplicationScope
    @Provides
    internal fun provideLocalDataSource(codewarsApplication: CodewarsApplication): LocalDataSource =
            LocalDataSourceImp(Room.databaseBuilder(codewarsApplication,
                    CodewarsDatabase::class.java, "codewars.db")
                    .build())

    @ApplicationScope
    @Provides
    internal fun provideCodewarsRepository(localDataSource: LocalDataSource,
                                           remoteDataSource: RemoteDataSource,
                                           connectivityManager: ConnectivityManager): CodewarsRepository =
        CodewarsRepositoryImp(remoteDataSource, localDataSource, connectivityManager)

    @ApplicationScope
    @Provides
    internal fun provideConnectivityManager(codewarsApplication: CodewarsApplication) =
        codewarsApplication.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}