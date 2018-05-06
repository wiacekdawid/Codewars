package com.wiacekdawid.codewars.data.remote.api

import com.google.gson.annotations.SerializedName

/**
 * Created by dawidwiacek on 29/04/2018.
 */

data class MemberDto(@SerializedName("username")
                     var userName: String?,
                     @SerializedName("name")
                     var name: String?,
                     @SerializedName("ranks")
                     var ranks: RanksDto?)