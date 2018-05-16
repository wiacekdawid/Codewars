package com.wiacekdawid.codewars.data.remote.mappers

import com.wiacekdawid.codewars.data.local.model.Member
import com.wiacekdawid.codewars.data.remote.api.MemberDto
import com.wiacekdawid.codewars.data.remote.api.model.ProgrammingLanguageWrapperDto

/**
 * Created by dawidwiacek on 11/05/2018.
 */

object MemberDtoToMemberMapper {

    fun transform(memberDto: MemberDto): Member {
        return with(memberDto) {
            Member(userName = userName
                    ?: Member.DEFAULT_USER_NAME,
                    name = name,
                    rank = ranks?.rank?.rank
                            ?: Member.DEFAULT_RANK,
                    bestLanguage = getBestLanguge(ranks?.languages))
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
}