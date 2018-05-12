package com.wiacekdawid.codewars.ui.challengeslist

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wiacekdawid.codewars.BR
import com.wiacekdawid.codewars.ui.AttachedCodewarsActivity

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class ChallengeItemViewModel(var userName: String = "",
                             var id: String = "",
                             var isCompleted: Boolean = true,
                             name: String = "",
                             val attachedCodewarsActivity: AttachedCodewarsActivity): BaseObservable() {

    @get:Bindable
    var name = name
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    fun onItemClick() {
        attachedCodewarsActivity.openChallengeDetails(userName, id, isCompleted)
    }

    fun setItem(challenge: Challenge) {
        name = challenge.name
        userName = challenge.userName
        id = challenge.id
        isCompleted = challenge.isCompleted
    }
}