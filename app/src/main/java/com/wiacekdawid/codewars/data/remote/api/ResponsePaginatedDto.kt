package com.wiacekdawid.codewars.data.remote.api

import com.google.gson.annotations.SerializedName

/**
 * Created by dawidwiacek on 02/05/2018.
 */

data class ResponsePaginatedDto(@SerializedName("data")
                    var data: List<CompletedChallengeDto>)