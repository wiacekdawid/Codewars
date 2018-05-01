package com.wiacekdawid.codewars.data.repository

import com.wiacekdawid.codewars.data.local.LocalDataSource
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.remote.RemoteDataSource
import io.reactivex.Single

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class CodewarsRepository(val remoteDataSource: RemoteDataSource,
                         val localDataSource: LocalDataSource) {

    fun getMember(searchText: String?): Single<Member> {
        return remoteDataSource.getMember(searchText)
                .map {
                    Member(uid = "0", name = it?.body()?.name, username = it?.body()?.name)
                }

    }
}