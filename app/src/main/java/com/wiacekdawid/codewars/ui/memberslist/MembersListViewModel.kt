package com.wiacekdawid.codewars.ui.memberslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class MembersListViewModel(private val codewarsRepository: CodewarsRepository): ViewModel() {
    var foundedMember: MutableLiveData<String> = MutableLiveData()
    var listOfLastSearchedMembers: MutableLiveData<List<Member>>? = MutableLiveData()
    var selectMember = SingleLiveEvent<String>()

    var searchText: ObservableField<String> = ObservableField()

    fun searchMember() {
        codewarsRepository.getMember(searchText = searchText.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it != null) {
                        foundedMember.postValue(it.username)
                    }
                }, {
                    Timber.e(it)
                })
    }

    fun clickOnMember() {
        selectMember.postValue(foundedMember?.value)
    }
}