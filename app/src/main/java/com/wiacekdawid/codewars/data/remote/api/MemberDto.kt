package com.wiacekdawid.codewars.data.remote.api

import com.google.gson.annotations.SerializedName
import com.wiacekdawid.codewars.data.remote.api.model.RanksDto

/**
 * Created by dawidwiacek on 29/04/2018.
 */

data class MemberDto(@SerializedName("username")
                     var userName: String?,
                     @SerializedName("name")
                     var name: String?,
                     @SerializedName("ranks")
                     var ranks: RanksDto?)