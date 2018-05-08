package com.wiacekdawid.codewars.ui.challengeslist

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wiacekdawid.codewars.BR

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class ChallengeItemViewModel(name: String = ""): BaseObservable() {

    @get:Bindable
    var name = name
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    fun setItem(challage: Challenge) {
        name = challage?.name
    }
}