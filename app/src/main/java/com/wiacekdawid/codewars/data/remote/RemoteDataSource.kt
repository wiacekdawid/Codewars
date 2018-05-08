package com.wiacekdawid.codewars.data.remote

import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.remote.api.AuthoredChallengesResponseDto
import com.wiacekdawid.codewars.data.remote.api.CodewarsService
import com.wiacekdawid.codewars.data.remote.api.CompletedChallengesResponseDto
import com.wiacekdawid.codewars.data.remote.api.ProgrammingLanguageWrapperDto
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
                        member.lastSearchTime = System.currentTimeMillis()
                        member.bestLanguage = getBestLanguge(it.ranks?.languages)
                    }
                    member
                }
    }

    private fun getBestLanguge(list: List<ProgrammingLanguageWrapperDto>?): String? {
        var bestLanguage: String? = null
        var maxRank = Integer.MIN_VALUE
        list?.forEach{
            if(it.rank > maxRank) {
                bestLanguage = it.name + " " + it.score
            }
        }

        return bestLanguage
    }

    fun getCompletedChallenges(username: String, page: Int): Single<CompletedChallengesResponseDto> {
        return codewarsService.getCompletedChallenges(username, page)
    }

    fun getAuthoredChallenges(username: String): Single<AuthoredChallengesResponseDto> {
        return codewarsService.getAuthoredChallenges(username)
    }
}