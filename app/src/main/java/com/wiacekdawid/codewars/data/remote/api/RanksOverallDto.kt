package com.wiacekdawid.codewars.data.remote.api

import com.google.gson.annotations.SerializedName

/**
 * Created by dawidwiacek on 29/04/2018.
 */

data class RanksOverallDto(@SerializedName("rank")
                           var rank: Int = Int.MIN_VALUE,
                           @SerializedName("name")
                           var name: String?)