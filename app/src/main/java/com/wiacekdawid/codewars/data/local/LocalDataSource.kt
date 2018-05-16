package com.wiacekdawid.codewars.data.local

import android.arch.paging.DataSource
import com.wiacekdawid.codewars.data.local.model.AuthoredChallenge
import com.wiacekdawid.codewars.data.local.model.CompletedChallenge
import com.wiacekdawid.codewars.data.local.model.Member
import com.wiacekdawid.codewars.data.repository.RepositoryResponse
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by dawidwiacek on 10/05/2018.
 */

interface LocalDataSource {

    companion object {
        const val DEFAULT_EMPTY_USER_NAME = "DEFAULT_EMPTY_USER_NAME"
        const val DEFAULT_EMPTY_STRING_ID = "DEFAULT_EMPTY_STRING_ID"
    }

    // MEMBER

    fun getMember(userName: String): Single<RepositoryResponse<Member>>

    fun insertMember(member: Member): Completable

    fun getMembersSortedByLastSearchedTime(limit: Int): Single<RepositoryResponse<List<Member>>>

    fun getMembersSortedByRank(limit: Int): Single<RepositoryResponse<List<Member>>>

    // COMPLETED CHALLENGES

    fun getAllCompletedChallengesForUserName(userName: String): DataSource.Factory<Int, CompletedChallenge>

    fun insertCompletedChallenges(challenges: List<CompletedChallenge>?): Completable

    fun getCompletedChallengeForId(id: String): Single<RepositoryResponse<CompletedChallenge>>

    // AUTHORED CHALLENGES

    fun getAllAuthoredChallengesForUserName(userName: String): DataSource.Factory<Int, AuthoredChallenge>

    fun insertAuthoredChallenges(challenges: List<AuthoredChallenge>?): Completable

    fun getAuthoredChallengeForId(id: String): Single<RepositoryResponse<AuthoredChallenge>>
}