package com.wiacekdawid.codewars.data.remote.api

/**
 * Created by dawidwiacek on 08/05/2018.
 */

data class ProgrammingLanguageWrapperDto(val name: String,
                                         var rank: Int = Int.MIN_VALUE,
                                         val score: Int)