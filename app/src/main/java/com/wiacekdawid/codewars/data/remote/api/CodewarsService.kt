package com.wiacekdawid.codewars.data.remote.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by dawidwiacek on 29/04/2018.
 */

interface CodewarsService {
    @GET(CodewarsApiContract.USERS_ENDPOINT)
    fun getMember(@Path(CodewarsApiContract.USERNAME_PARAM) username: String): Single<Response<MemberDto>>

    @GET(CodewarsApiContract.COMPLETED_CHALLENGES_ENDPOINT)
    fun getCompletedChallenges(@Path(CodewarsApiContract.USERNAME_PARAM) username: String,
                               @Query(CodewarsApiContract.PAGE_PARAM) page: Int): Single<Response<CompletedChallengesResponseDto>>

    @GET(CodewarsApiContract.AUTHORED_CHALLENGES_ENDPOINT)
    fun getAuthoredChallenges(@Path(CodewarsApiContract.USERNAME_PARAM) username: String): Single<Response<AuthoredChallengesResponseDto>>
}