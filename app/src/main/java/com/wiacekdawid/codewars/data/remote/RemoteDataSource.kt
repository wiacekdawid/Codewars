package com.wiacekdawid.codewars.data.remote

import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.remote.api.AuthoredChallengeDto
import com.wiacekdawid.codewars.data.remote.api.CodewarsService
import com.wiacekdawid.codewars.data.remote.api.ResponsePaginatedDto
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class RemoteDataSource(private val codewarsService: CodewarsService) {

    companion object {
        const val DEFAULT_ITEM_PER_PAGE = 200
    }

    fun getMember(searchUsername: String?): Single<Member> {
        return codewarsService.getMember(searchUsername)
                .doOnError{
                    Timber.e(it)
                }
                .map {
                    var member = Member()
                    if(it.userName != null) {
                        member.userName = it.userName as String
                        member.name = it.name
                        member.rank = it.ranks?.rank?.rank ?: Member.DEFAULT_RANK
                    }
                    member
                }
    }

    fun getCompletedChallenges(username: String, page: Int): Observable<ResponsePaginatedDto> {
        return codewarsService.getCompletedChallenges(username, page)
    }

    fun getAuthoredChallenges(username: String, page: Int): Observable<List<AuthoredChallengeDto>> {
        return codewarsService.getAuthoredChallenges(username, page)
    }
}