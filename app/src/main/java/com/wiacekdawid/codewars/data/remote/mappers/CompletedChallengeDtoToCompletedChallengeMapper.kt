package com.wiacekdawid.codewars.data.remote.mappers

import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.data.remote.api.CompletedChallengeDto

/**
 * Created by dawidwiacek on 12/05/2018.
 */

object CompletedChallengeDtoToCompletedChallengeMapper {

    fun transform(completedChallengeDto: CompletedChallengeDto, userName: String): CompletedChallenge {
        return with(completedChallengeDto) {
            CompletedChallenge(id = id, name = name, userName = userName)
        }
    }
}