package com.wiacekdawid.codewars.data.local

import android.arch.paging.DataSource
import com.wiacekdawid.codewars.data.repository.RepositoryResponse
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by dawidwiacek on 10/05/2018.
 */

class LocalDataSource(val codewarsDatabase: CodewarsDatabase) {

    companion object {
        const val DEFAULT_EMPTY_ID = -1
        const val DEFAULT_EMPTY_STRING_ID = "DEFAULT_EMPTY_STRING_ID"
    }

    // MEMBER

    fun getMember(userName: String): Single<RepositoryResponse<Member>> {
        return codewarsDatabase.membersDao()
                .getMember(userName)
                .switchIfEmpty(Single.just(Member(id = DEFAULT_EMPTY_ID)))
                .map {
                    with(it) {
                        var response = RepositoryResponse<Member>()
                        if(id == DEFAULT_EMPTY_ID){
                            response.code = RepositoryResponse.ResponseCode.NO_DATA
                        }
                        else {
                            with(response) {
                                code = RepositoryResponse.ResponseCode.SUCCESS
                                data = it
                            }
                        }
                        response
                    }
                }
    }

    fun insertMember(member: Member): Completable = Completable.fromAction {
            codewarsDatabase.membersDao().insert(member)
        }

    fun getMembersSortedByLastSearchedTime(limit: Int): Single<RepositoryResponse<List<Member>>> {
        return codewarsDatabase.membersDao().getMembersSortedByLastSearchedTime(limit)
                .switchIfEmpty(Single.just(emptyList()))
                .map {
                    var response = RepositoryResponse<List<Member>>()
                    if (it.isEmpty()) {
                        response.code = RepositoryResponse.ResponseCode.NO_DATA
                    } else {
                        response.code = RepositoryResponse.ResponseCode.SUCCESS
                        response.data = it
                    }
                    response
                }
    }

    fun getMembersSortedByRank(limit: Int): Single<RepositoryResponse<List<Member>>> {
        return codewarsDatabase.membersDao().getMembersSortedByLastSearchedTime(limit)
                .switchIfEmpty(Single.just(emptyList()))
                .map {
                    var response = RepositoryResponse<List<Member>>()
                    if (it.isEmpty()) {
                        response.code = RepositoryResponse.ResponseCode.NO_DATA
                    } else {
                        response.code = RepositoryResponse.ResponseCode.SUCCESS
                        response.data = it
                    }
                    response
                }
    }

    // COMPLETED CHALLENGES
    fun getAllCompletedChallengesForUserName(userName: String): DataSource.Factory<Int, CompletedChallenge> {
        return codewarsDatabase.completedChallengeDao().getAllCompletedChallengesForUserName(userName)
    }

    fun insertCompletedChallenges(challenges: List<CompletedChallenge>?): Completable = Completable.fromAction {
        challenges?.forEach {
            codewarsDatabase.completedChallengeDao().insert(it)
        }
    }

    fun getCompletedChallengeForId(id: String): Single<RepositoryResponse<CompletedChallenge>> =
            codewarsDatabase.completedChallengeDao()
                    .getCompletedChallengeForId(id)
                    .switchIfEmpty(Single.just(CompletedChallenge(id = DEFAULT_EMPTY_STRING_ID)))
                    .map {
                        with(it) {
                            var response = RepositoryResponse<CompletedChallenge>()
                            if(id == DEFAULT_EMPTY_STRING_ID){
                                response.code = RepositoryResponse.ResponseCode.NO_DATA
                            }
                            else {
                                kotlin.with(response) {
                                    code = RepositoryResponse.ResponseCode.SUCCESS
                                    data = it
                                }
                            }
                            response
                        }
                    }

    // AUTHORED CHALLENGES
    fun getAllAuthoredChallengesForUserName(userName: String): DataSource.Factory<Int, AuthoredChallenge> {
        return codewarsDatabase.authoredChallengeDao().getAllAuthoredChallengesForUserName(userName)
    }

    fun insertAuthoredChallenges(challenges: List<AuthoredChallenge>?): Completable = Completable.fromAction {
        challenges?.forEach {
            codewarsDatabase.authoredChallengeDao().insert(it)
        }
    }

    fun getAuthoredChallengeForId(id: String): Single<RepositoryResponse<AuthoredChallenge>> =
            codewarsDatabase.authoredChallengeDao()
                    .getAuthoredChallengeForId(id)
                    .switchIfEmpty(Single.just(AuthoredChallenge(id = DEFAULT_EMPTY_STRING_ID)))
                    .map {
                        with(it) {
                            var response = RepositoryResponse<AuthoredChallenge>()
                            if(id == DEFAULT_EMPTY_STRING_ID){
                                response.code = RepositoryResponse.ResponseCode.NO_DATA
                            }
                            else {
                                kotlin.with(response) {
                                    code = RepositoryResponse.ResponseCode.SUCCESS
                                    data = it
                                }
                            }
                            response
                        }
                    }
}