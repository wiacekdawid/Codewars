package com.wiacekdawid.codewars.ui.memberslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class MembersListViewModel(private val codewarsRepository: CodewarsRepository): ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    var errorMsgVisibility: MutableLiveData<Boolean> = MutableLiveData()
    var noFoundMemberMsgVisibility: MutableLiveData<Boolean> = MutableLiveData()
    var foundedMember: MutableLiveData<String> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var lastSearchedMembers: MutableLiveData<List<Member>> = MutableLiveData()
    var lastSearchedMembersVisibility: MutableLiveData<Boolean> = MutableLiveData()
    var selectMember = SingleLiveEvent<String>()
    var searchText: MutableLiveData<String> = MutableLiveData()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun refreshLastSearchedMembersSortedByDate() {
        compositeDisposable.add(codewarsRepository.getLastSearchedMembersSortedByDate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    lastSearchedMembers.postValue(it.take(5))
                    lastSearchedMembersVisibility.postValue(true)
                }, {
                    Timber.e(it)
                    lastSearchedMembersVisibility.postValue(false)
                    lastSearchedMembers.postValue(listOf())
                },{
                    lastSearchedMembersVisibility.postValue(false)
                    lastSearchedMembers.postValue(listOf())
                })
        )
    }

    fun refreshLastSearchedMembersSortedByRank() {
        compositeDisposable.add(codewarsRepository.getLastSearchedMembersSortedByRank()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    lastSearchedMembers.postValue(it.take(5))
                    lastSearchedMembersVisibility.postValue(true)
                }, {
                    Timber.e(it)
                    lastSearchedMembersVisibility.postValue(false)
                    lastSearchedMembers.postValue(listOf())
                },{
                    lastSearchedMembersVisibility.postValue(false)
                    lastSearchedMembers.postValue(listOf())
                })
        )
    }

    fun searchMember() {
        searchText.value?.let {
            if(!it.isEmpty()) {
                loading.postValue(true)
                foundedMember.postValue("")
                errorMsgVisibility.postValue(false)
                noFoundMemberMsgVisibility.postValue(false)
                compositeDisposable.add(codewarsRepository.getMember(userName = it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError {
                            if((it as? HttpException)?.code() == 404) {
                                noFoundMemberMsgVisibility.postValue(true)
                            }
                        }
                        .subscribe({
                            member: Member? ->
                            member?.let {
                                if(member.userName == Member.DEFAULT_USER_NAME) {
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
                )
            }
        }
    }

    fun clickOnMember() {
        selectMember.postValue(foundedMember?.value)
    }
}