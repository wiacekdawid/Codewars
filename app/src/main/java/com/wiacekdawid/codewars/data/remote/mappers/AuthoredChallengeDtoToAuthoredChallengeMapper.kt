package com.wiacekdawid.codewars.data.remote.mappers

import com.wiacekdawid.codewars.data.local.model.AuthoredChallenge
import com.wiacekdawid.codewars.data.remote.api.model.AuthoredChallengeDto

/**
 * Created by dawidwiacek on 12/05/2018.
 */

object AuthoredChallengeDtoToAuthoredChallengeMapper {

    fun transform(authoredChallengeDto: AuthoredChallengeDto, userName: String): AuthoredChallenge {
        return with(authoredChallengeDto) {
            AuthoredChallenge(id = id, name = name, userName = userName)
        }
    }
}