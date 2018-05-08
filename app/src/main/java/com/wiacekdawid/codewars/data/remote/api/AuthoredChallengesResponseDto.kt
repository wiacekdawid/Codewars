package com.wiacekdawid.codewars.data.remote.api

import com.google.gson.annotations.SerializedName

/**
 * Created by dawidwiacek on 08/05/2018.
 */

data class AuthoredChallengesResponseDto(@SerializedName("data")
                                var data: List<AuthoredChallengeDto>)