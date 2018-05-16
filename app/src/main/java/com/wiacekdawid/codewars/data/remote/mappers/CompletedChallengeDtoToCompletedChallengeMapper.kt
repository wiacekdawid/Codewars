package com.wiacekdawid.codewars.data.remote.mappers

import com.wiacekdawid.codewars.data.local.model.CompletedChallenge
import com.wiacekdawid.codewars.data.remote.api.model.CompletedChallengeDto

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