package com.wiacekdawid.codewars.data.repository

import android.arch.paging.DataSource
import android.net.ConnectivityManager
import com.wiacekdawid.codewars.data.local.model.AuthoredChallenge
import com.wiacekdawid.codewars.data.local.model.CompletedChallenge
import com.wiacekdawid.codewars.data.local.LocalDataSource
import com.wiacekdawid.codewars.data.local.model.Member
import com.wiacekdawid.codewars.data.remote.RemoteDataSource
import com.wiacekdawid.codewars.data.remote.api.model.CompletedChallengesResponseDto
import com.wiacekdawid.codewars.data.remote.mappers.CompletedChallengeDtoToCompletedChallengeMapper
import io.reactivex.Single

/**
 * Created by dawidwiacek on 16/05/2018.
 */

class CodewarsRepositoryImp(private val remoteDataSource: RemoteDataSource,
                            private val localDataSource: LocalDataSource,
                            private val connectivityManager: ConnectivityManager,
                            private var memberCache: HashMap<String, Member> = HashMap()): CodewarsRepository {

    private var currentPage = 0

    override fun resetPaginationData() {
        currentPage = 0
    }

    override fun getLastSearchedMembersSortedByDate(limit: Int): Single<RepositoryResponse<List<Member>>> = localDataSource.getMembersSortedByLastSearchedTime(limit)

    override fun getLastSearchedMembersSortedByRank(limit: Int): Single<RepositoryResponse<List<Member>>> = localDataSource.getMembersSortedByRank(limit)

    override fun getMember(userName: String): Single<RepositoryResponse<Member>> {

        val member = memberCache[userName]

        // if member is in our cache we return it
        if(member != null) {
            return Single.just(RepositoryResponse(data = member))
        }
        else {
            val activeNetwork = connectivityManager.activeNetworkInfo

            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting) {

                return localDataSource.getMember(userName)
                        .flatMap {
                            // if its not success try from remote data source
                            if(it.code != RepositoryResponse.ResponseCode.SUCCESS) {
                                return@flatMap remoteDataSource.getMember(userName)
                                        // on success api call we store member in DB and local cache
                                        .doOnSuccess {
                                            it?.data?.let {
                                                localDataSource.insertMember(it).subscribe()
                                                memberCache[it.userName] = it
                                            }
                                        }
                            }
                            Single.just(it)
                        }
            }
            return localDataSource.getMember(userName)
        }
    }

    override fun getCompletedChallenges(username: String): DataSource.Factory<Int, CompletedChallenge> {
        return localDataSource.getAllCompletedChallengesForUserName(username)
    }

    override fun getAuthoredChallenges(username: String): DataSource.Factory<Int, AuthoredChallenge> {
        return localDataSource.getAllAuthoredChallengesForUserName(username)
    }

    override fun refreshAuthoredChallenges(userName: String): Single<RepositoryResponse<List<AuthoredChallenge>>> {
        return remoteDataSource
                .getAuthoredChallenges(userName)
                .doOnSuccess {
                    if(it.code == RepositoryResponse.ResponseCode.SUCCESS) {
                        localDataSource.insertAuthoredChallenges(it.data).subscribe()
                    }
                }
    }

    override fun loadMoreCompletedChallenges(userName: String): Single<RepositoryResponse<CompletedChallengesResponseDto>> {
        return remoteDataSource
                .getCompletedChallenges(userName, currentPage)
                .doOnSuccess {
                    if(it.code == RepositoryResponse.ResponseCode.SUCCESS) {
                        if(it.data?.data?.isEmpty() == false) {
                            currentPage++
                            it.data?.data?.let {
                                val listOfCompletedChallenge: MutableList<CompletedChallenge> = mutableListOf()
                                it.forEach {
                                    listOfCompletedChallenge.add(CompletedChallengeDtoToCompletedChallengeMapper.transform(it, userName))
                                }
                                localDataSource.insertCompletedChallenges(listOfCompletedChallenge)
                            }
                        }
                        else {
                            currentPage = 0
                        }
                    }
                    else {
                        currentPage = 0
                    }
                }
    }

    override fun getCompletedChallenge(id: String): Single<RepositoryResponse<CompletedChallenge>> =
            localDataSource.getCompletedChallengeForId(id)

    override fun getAuthoredChallenge(id: String): Single<RepositoryResponse<AuthoredChallenge>> =
            localDataSource.getAuthoredChallengeForId(id)

}