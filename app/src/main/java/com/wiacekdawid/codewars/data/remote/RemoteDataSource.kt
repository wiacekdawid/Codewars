package com.wiacekdawid.codewars.data.remote

import com.wiacekdawid.codewars.data.remote.api.CodewarsService
import com.wiacekdawid.codewars.data.remote.api.MemberDto
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class RemoteDataSource(private val codewarsService: CodewarsService) {
    fun getMember(searchUsername: String?): Single<Response<MemberDto>> {
        return codewarsService.getMember(searchUsername)
    }
}