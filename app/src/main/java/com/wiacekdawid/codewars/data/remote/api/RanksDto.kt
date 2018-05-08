package com.wiacekdawid.codewars.data.remote.api

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

/**
 * Created by dawidwiacek on 29/04/2018.
 */

data class RanksDto(@SerializedName("overall")
                    var rank: RanksOverallDto?,
                    @SerializedName("languages")
                    @JsonAdapter(ProgrammingLanguagesDeserializer::class)
                    var languages: List<ProgrammingLanguageWrapperDto>)