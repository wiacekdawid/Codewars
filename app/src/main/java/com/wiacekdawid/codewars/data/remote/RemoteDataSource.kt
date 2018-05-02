package com.wiacekdawid.codewars.data.remote

import com.wiacekdawid.codewars.data.remote.api.*
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class RemoteDataSource(private val codewarsService: CodewarsService) {

    companion object {
        const val DEFAULT_ITEM_PER_PAGE = 200
    }

    fun getMember(searchUsername: String?): Single<Response<MemberDto>> {
        return codewarsService.getMember(searchUsername)
    }

    fun getCompletedChallenges(username: String, page: Int): Single<Response<ResponsePaginatedDto>> {
        return codewarsService.getCompletedChallenges(username, page)
    }

    fun getAuthoredChallenges(username: String, page: Int): Single<Response<List<AuthoredChallengeDto>>> {
        return codewarsService.getAuthoredChallenges(username, page)
    }
}