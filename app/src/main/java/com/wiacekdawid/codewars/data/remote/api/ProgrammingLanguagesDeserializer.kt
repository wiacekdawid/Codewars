package com.wiacekdawid.codewars.data.remote.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Created by dawidwiacek on 07/05/2018.
 */

class ProgrammingLanguagesDeserializer: JsonDeserializer<List<ProgrammingLanguageWrapperDto>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<ProgrammingLanguageWrapperDto> {
        var jsonObject = json?.asJsonObject
        var languages = ArrayList<ProgrammingLanguageWrapperDto>()
        jsonObject?.entrySet()?.forEach {
            var languageDto: ProgrammingLanguageDto? = context?.deserialize(it.value, ProgrammingLanguageDto::class.java)
            languageDto?.let { itDto ->
                var language = ProgrammingLanguageWrapperDto(name = it.key, rank = itDto.rank, score = itDto.score)
                languages.add(language)
            }
        }
        return languages
    }
}