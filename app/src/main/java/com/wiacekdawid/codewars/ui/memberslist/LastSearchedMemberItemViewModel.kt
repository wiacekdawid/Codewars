package com.wiacekdawid.codewars.ui.memberslist

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wiacekdawid.codewars.BR
import com.wiacekdawid.codewars.data.local.model.Member
import com.wiacekdawid.codewars.ui.AttachedCodewarsActivity

/**
 * Created by dawidwiacek on 06/05/2018.
 */

class LastSearchedMemberItemViewModel(userName: String = "",
                                      rank: String = "",
                                      bestLanguageAndScore: String = "",
                                      val attachedCodewarsActivity: AttachedCodewarsActivity?): BaseObservable() {

    @get:Bindable
    var userName = userName
        set(value) {
            field = value
            notifyPropertyChanged(BR.userName)
        }

    @get:Bindable
    var rank = rank
        set(value) {
            field = value
            notifyPropertyChanged(BR.rank)
        }

    @get:Bindable
    var bestLanguageAndScore = bestLanguageAndScore
        set(value) {
            field = value
            notifyPropertyChanged(BR.bestLanguageAndScore)
        }

    fun onItemClick() {
        attachedCodewarsActivity?.openChallenges(userName)
    }

    fun setItem(member: Member) {
        userName = member.userName
        rank = member.rank.toString()
        bestLanguageAndScore = member.bestLanguage ?: ""
    }
}