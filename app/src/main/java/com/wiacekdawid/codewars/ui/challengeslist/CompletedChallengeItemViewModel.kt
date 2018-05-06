package com.wiacekdawid.codewars.ui.challengeslist

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wiacekdawid.codewars.BR
import com.wiacekdawid.codewars.data.local.CompletedChallenge

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class CompletedChallengeItemViewModel(name: String = ""): BaseObservable() {

    @get:Bindable
    var name = name
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    fun setItem(completedChallage: CompletedChallenge?) {
        name = completedChallage?.name ?: completedChallage?.userName ?: "test"
    }
}