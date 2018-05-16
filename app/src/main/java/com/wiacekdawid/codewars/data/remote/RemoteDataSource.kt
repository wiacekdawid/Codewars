package com.wiacekdawid.codewars.data.remote

import com.wiacekdawid.codewars.data.local.model.AuthoredChallenge
import com.wiacekdawid.codewars.data.local.model.Member
import com.wiacekdawid.codewars.data.remote.api.model.CompletedChallengesResponseDto
import com.wiacekdawid.codewars.data.repository.RepositoryResponse
import io.reactivex.Single

/**
 * Created by dawidwiacek on 29/04/2018.
 */

interface RemoteDataSource {

    fun getMember(searchUsername: String): Single<RepositoryResponse<Member>>

    fun getCompletedChallenges(username: String, page: Int): Single<RepositoryResponse<CompletedChallengesResponseDto>>

    fun getAuthoredChallenges(username: String): Single<RepositoryResponse<List<AuthoredChallenge>>>
}