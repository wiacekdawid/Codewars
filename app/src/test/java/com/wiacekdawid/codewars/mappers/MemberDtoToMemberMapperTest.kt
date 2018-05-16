package com.wiacekdawid.codewars.mappers

import com.wiacekdawid.codewars.data.remote.api.MemberDto
import com.wiacekdawid.codewars.data.remote.api.model.ProgrammingLanguageWrapperDto
import com.wiacekdawid.codewars.data.remote.api.model.RanksDto
import com.wiacekdawid.codewars.data.remote.api.model.RanksOverallDto
import com.wiacekdawid.codewars.data.remote.mappers.MemberDtoToMemberMapper
import junit.framework.Assert
import org.junit.Test

/**
 * Created by dawidwiacek on 14/05/2018.
 */

class MemberDtoToMemberMapperTest {

    @Test
    fun successTransformAuthoredChalleneDtoToAuthoredChallenge() {
        //given
        val programmingLanguage1Dto = ProgrammingLanguageWrapperDto(rank = 5, name = "lan1", score = 1)
        val programmingLanguage2Dto = ProgrammingLanguageWrapperDto(rank = 8, name = "lan2", score = 2)
        val ranksOverallDto = RanksOverallDto(rank = 5, name = "name")
        val ranks = RanksDto(ranksOverallDto, listOf(programmingLanguage1Dto, programmingLanguage2Dto))
        val memberDto = MemberDto(userName = "userName", name = "name", ranks = ranks)

        //when
        val member = MemberDtoToMemberMapper.transform(memberDto)

        //expect
        with(member) {
            Assert.assertEquals(userName, memberDto.userName)
            Assert.assertEquals(name, memberDto.name)
            Assert.assertEquals(rank, ranksOverallDto.rank)
            Assert.assertEquals(bestLanguage, programmingLanguage2Dto.name + " " + programmingLanguage2Dto.score)
        }
    }
}