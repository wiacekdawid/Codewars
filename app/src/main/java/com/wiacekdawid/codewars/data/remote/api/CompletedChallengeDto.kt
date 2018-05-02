package com.wiacekdawid.codewars.data.remote.api

import com.google.gson.annotations.SerializedName

/**
 * Created by dawidwiacek on 01/05/2018.
 */

data class CompletedChallengeDto(@SerializedName("id")
                                 var id: String,
                                 @SerializedName("name")
                                 var name: String?)