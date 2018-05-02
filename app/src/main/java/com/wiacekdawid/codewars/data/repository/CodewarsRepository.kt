package com.wiacekdawid.codewars.data.repository

import android.arch.paging.DataSource
import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.data.local.LocalDataSource
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.remote.RemoteDataSource
import com.wiacekdawid.codewars.data.remote.api.CompletedChallengeDto
import com.wiacekdawid.codewars.data.remote.api.ResponsePaginatedDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class CodewarsRepository(val remoteDataSource: RemoteDataSource,
                         val localDataSource: LocalDataSource) {

    fun getMember(searchText: String?): Single<Member> {
        return remoteDataSource.getMember(searchText)
                .map {
                    it.body()?.let {
                        var member = Member(name = it.name, username = it.userName)
                        localDataSource.membersDao().insert(member = member)
                        member
                    }
                }
    }

    fun getCompletedChallenges(username: String): DataSource.Factory<Int, CompletedChallenge> {
        return localDataSource.completedChallengeDao().getAllCompletedChallengesForMember(username)
    }

    fun fetchCompletedChallenges(memberId: String): Single<Response<ResponsePaginatedDto>> {
        return localDataSource
                .completedChallengeDao()
                .countCompletedChallengesForMember(memberId)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    var page: Int = 0
                    if(it > 0) {
                        page = it / RemoteDataSource.DEFAULT_ITEM_PER_PAGE
                    }
                    remoteDataSource.getCompletedChallenges(memberId, page)
                }
    }

    fun addCompletedChallengesToDB(challenges: List<CompletedChallenge>): Completable {
        return Completable.fromRunnable({
            for(completedChallenge in challenges) {
                localDataSource.completedChallengeDao().insert(completedChallenge)
            }
        })
    }
}