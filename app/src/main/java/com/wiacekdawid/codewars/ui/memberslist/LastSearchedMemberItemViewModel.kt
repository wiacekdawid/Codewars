package com.wiacekdawid.codewars.ui.memberslist

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wiacekdawid.codewars.BR
import com.wiacekdawid.codewars.data.local.Member

/**
 * Created by dawidwiacek on 06/05/2018.
 */

class LastSearchedMemberItemViewModel(userName: String = ""): BaseObservable() {

    @get:Bindable
    var userName = userName
        set(value) {
            field = value
            notifyPropertyChanged(BR.userName)
        }

    fun setItem(member: Member) {
        userName = member.userName
    }
}