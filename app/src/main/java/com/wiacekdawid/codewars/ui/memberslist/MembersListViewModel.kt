package com.wiacekdawid.codewars.ui.memberslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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
    var errorMsgVisibility: MutableLiveData<Boolean> = MutableLiveData()
    var noFoundMemberMsgVisibility: MutableLiveData<Boolean> = MutableLiveData()
    var foundedMember: MutableLiveData<String> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var listOfLastSearchedMembers: MutableLiveData<List<Member>>? = MutableLiveData()
    var selectMember = SingleLiveEvent<String>()

    var searchText: MutableLiveData<String> = MutableLiveData()

    fun searchMember() {
        searchText.value?.let {
            if(!it.isEmpty()) {
                loading.postValue(true)
                foundedMember.postValue("")
                errorMsgVisibility.postValue(false)
                noFoundMemberMsgVisibility.postValue(false)
                codewarsRepository.getMember(searchText = it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            member: Member? ->
                            member?.let {
                                if(member.userName.equals(Member.DEFAULT_USER_NAME)) {
                                    noFoundMemberMsgVisibility.postValue(true)
                                }
                                else {
                                    foundedMember.postValue(member.userName)
                                }
                            }
                            loading.postValue(false)

                        }, {
                            throwable: Throwable ->
                            Timber.e(throwable)
                            loading.postValue(false)
                        })
            }
        }
    }

    fun clickOnMember() {
        selectMember.postValue(foundedMember?.value)
    }
}