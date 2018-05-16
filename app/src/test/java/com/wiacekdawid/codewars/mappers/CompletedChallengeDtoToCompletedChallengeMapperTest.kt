package com.wiacekdawid.codewars.mappers

import com.wiacekdawid.codewars.data.remote.api.model.CompletedChallengeDto
import com.wiacekdawid.codewars.data.remote.mappers.CompletedChallengeDtoToCompletedChallengeMapper
import junit.framework.Assert
import org.junit.Test

/**
 * Created by dawidwiacek on 14/05/2018.
 */

class CompletedChallengeDtoToCompletedChallengeMapperTest {

    @Test
    fun successTransformAuthoredChalleneDtoToAuthoredChallenge() {
        //given
        val completedChallengeDto = CompletedChallengeDto(id = "completedChallengeId", name = "completedChallengeName")
        val userNameDto = "completedChallengeUserName"

        //when
        val completedChallenge = CompletedChallengeDtoToCompletedChallengeMapper.transform(completedChallengeDto, userNameDto)

        //expect
        with(completedChallenge) {
            Assert.assertEquals(id, completedChallengeDto.id)
            Assert.assertEquals(name, completedChallengeDto.name)
            Assert.assertEquals(userName, userNameDto)
        }
    }
}