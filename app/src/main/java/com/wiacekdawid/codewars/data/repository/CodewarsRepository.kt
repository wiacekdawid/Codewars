package com.wiacekdawid.codewars.data.repository

import android.arch.paging.DataSource
import android.net.ConnectivityManager
import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.data.local.LocalDataSource
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.remote.RemoteDataSource
import com.wiacekdawid.codewars.data.remote.api.ResponsePaginatedDto
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


/**
 * Created by dawidwiacek on 29/04/2018.
 */

class CodewarsRepository(val remoteDataSource: RemoteDataSource,
                         val localDataSource: LocalDataSource,
                         val connectivityManager: ConnectivityManager) {

    private var memberList: HashMap<String, Member> = HashMap()
    private var currentPage = 0

    fun resetPaginationData() {
        currentPage = 0
    }

    fun getLastSearchedMembers(): Maybe<List<Member>> = localDataSource.membersDao().getAllMembersSortedByLastSearchedTime()

    fun getMember(searchText: String): Single<Member> {

        var member = memberList[searchText]

        // if member is in our cache we return it
        if(member != null) {
            return Single.just(member)
        }
        else {
            val activeNetwork = connectivityManager.activeNetworkInfo

            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting) {

                return localDataSource.membersDao().getMember(searchText)
                        .switchIfEmpty(remoteDataSource.getMember(searchText))
                        .doOnSuccess {
                            if (it.userName != Member.DEFAULT_USER_NAME) {
                                localDataSource.membersDao().insert(it)
                                memberList[it.userName] = it
                            }
                        }
            }
            return localDataSource.membersDao().getMember(searchText)
                    .switchIfEmpty(Single.just(Member()))
        }
    }

    fun getCompletedChallenges(username: String): DataSource.Factory<Int, CompletedChallenge> {
        return localDataSource.completedChallengeDao().getAllCompletedChallengesForMember(username)
    }

    fun loadMoreCompletedChallenges(userName: String): Single<ResponsePaginatedDto> {
        return remoteDataSource
                .getCompletedChallenges(userName, currentPage)
                .doOnSuccess {
                    if(it.data?.size > 0) {
                        currentPage++
                    }
                    else {
                        currentPage = 0
                    }
                    it.data?.forEach {
                        localDataSource
                                .completedChallengeDao()
                                .insert(CompletedChallenge(id = it.id, name = it.name, userName = userName))
                    }
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