package com.wiacekdawid.codewars.data.repository

import android.arch.paging.DataSource
import com.wiacekdawid.codewars.data.local.model.AuthoredChallenge
import com.wiacekdawid.codewars.data.local.model.CompletedChallenge
import com.wiacekdawid.codewars.data.local.model.Member
import com.wiacekdawid.codewars.data.remote.api.model.CompletedChallengesResponseDto
import io.reactivex.Single


/**
 * Created by dawidwiacek on 29/04/2018.
 */

interface CodewarsRepository {

    fun resetPaginationData()

    fun getLastSearchedMembersSortedByDate(limit: Int): Single<RepositoryResponse<List<Member>>>

    fun getLastSearchedMembersSortedByRank(limit: Int): Single<RepositoryResponse<List<Member>>>

    fun getMember(userName: String): Single<RepositoryResponse<Member>>

    fun getCompletedChallenges(username: String): DataSource.Factory<Int, CompletedChallenge>

    fun getAuthoredChallenges(username: String): DataSource.Factory<Int, AuthoredChallenge>

    fun refreshAuthoredChallenges(userName: String): Single<RepositoryResponse<List<AuthoredChallenge>>>

    fun loadMoreCompletedChallenges(userName: String): Single<RepositoryResponse<CompletedChallengesResponseDto>>

    fun getCompletedChallenge(id: String): Single<RepositoryResponse<CompletedChallenge>>

    fun getAuthoredChallenge(id: String): Single<RepositoryResponse<AuthoredChallenge>>
}