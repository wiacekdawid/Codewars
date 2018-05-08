package com.wiacekdawid.codewars.data.remote.api

import com.google.gson.annotations.SerializedName

/**
 * Created by dawidwiacek on 07/05/2018.
 */

data class ProgrammingLanguageDto(@SerializedName("rank")
                                  var rank: Int = Int.MIN_VALUE,
                                  val score: Int)