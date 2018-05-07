package com.wiacekdawid.codewars.data.repository

import android.arch.paging.DataSource
import android.net.ConnectivityManager
import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.data.local.LocalDataSource
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.remote.RemoteDataSource
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single


/**
 * Created by dawidwiacek on 29/04/2018.
 */

class CodewarsRepository(val remoteDataSource: RemoteDataSource,
                         val localDataSource: LocalDataSource,
                         val connectivityManager: ConnectivityManager) {

    private var memberList: HashMap<String, Member> = HashMap()

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

    /*fun fetchCompletedChallenges(memberId: String): Observable<ResponsePaginatedDto> {
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
    }*/

    fun addCompletedChallengesToDB(challenges: List<CompletedChallenge>): Completable {
        return Completable.fromRunnable({
            for(completedChallenge in challenges) {
                localDataSource.completedChallengeDao().insert(completedChallenge)
            }
        })
    }
}