package com.wiacekdawid.codewars.mappers

import com.wiacekdawid.codewars.data.remote.api.model.AuthoredChallengeDto
import com.wiacekdawid.codewars.data.remote.mappers.AuthoredChallengeDtoToAuthoredChallengeMapper
import junit.framework.Assert
import org.junit.Test

/**
 * Created by dawidwiacek on 14/05/2018.
 */

class AuthoredChallengeDtoToAuthoredChallengeMapperTest {

    @Test
    fun successTransformAuthoredChalleneDtoToAuthoredChallenge() {
        //given
        val authoredChallengeDto = AuthoredChallengeDto(id = "authoredChallengeId", name = "authoredChallengeName")
        val userNameDto = "authoredChallengeUserName"

        //when
        val authoredChallenge = AuthoredChallengeDtoToAuthoredChallengeMapper.transform(authoredChallengeDto, userNameDto)

        //expect
        with(authoredChallenge) {
            Assert.assertEquals(id, authoredChallengeDto.id)
            Assert.assertEquals(name, authoredChallengeDto.name)
            Assert.assertEquals(userName, userNameDto)
        }
    }
}