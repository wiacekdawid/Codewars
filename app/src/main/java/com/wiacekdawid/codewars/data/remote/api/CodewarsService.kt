package com.wiacekdawid.codewars.data.remote.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by dawidwiacek on 29/04/2018.
 */

interface CodewarsService {
    @GET(CodewarsApiContract.USERS_ENDPOINT + "{searchUsername}")
    fun getMember(@Path("searchUsername") searchUsername: String?): Single<Response<MemberDto>>
}