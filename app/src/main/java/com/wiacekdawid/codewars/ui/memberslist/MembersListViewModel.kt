package com.wiacekdawid.codewars.ui.memberslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class MembersListViewModel(private val codewarsRepository: CodewarsRepository): ViewModel() {
    var foundedMember: MutableLiveData<String>? = null
    var listOfLastSearchedMembers: MutableLiveData<List<Member>>? = null

    var searchText: MutableLiveData<String>? = null

    fun searchMember() {
        codewarsRepository.getMember(searchText = searchText?.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it != null) {
                        foundedMember?.value = it.username
                    }
                }, {

                })
    }
}