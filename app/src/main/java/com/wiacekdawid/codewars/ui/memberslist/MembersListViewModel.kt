package com.wiacekdawid.codewars.ui.memberslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wiacekdawid.codewars.data.local.model.Member
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.data.repository.RepositoryResponse
import com.wiacekdawid.codewars.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class MembersListViewModel(private val codewarsRepository: CodewarsRepository): ViewModel() {

    private val LAST_SEARCHED_MEMBERS_LIMIT = 5

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
        compositeDisposable.add(codewarsRepository.getLastSearchedMembersSortedByDate(LAST_SEARCHED_MEMBERS_LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when(it.code) {
                        RepositoryResponse.ResponseCode.SUCCESS -> {
                            lastSearchedMembers.postValue(it.data)
                            lastSearchedMembersVisibility.postValue(true)
                        }
                        RepositoryResponse.ResponseCode.NO_DATA -> {
                            lastSearchedMembersVisibility.postValue(false)
                            lastSearchedMembers.postValue(it.data)
                        }
                        RepositoryResponse.ResponseCode.ERROR,
                        RepositoryResponse.ResponseCode.SERVER_ERROR -> {
                            //todo handle error in UI
                            lastSearchedMembersVisibility.postValue(false)
                            lastSearchedMembers.postValue(it.data)
                        }
                        else -> {
                            //todo handle error in UI
                            lastSearchedMembersVisibility.postValue(false)
                            lastSearchedMembers.postValue(it.data)
                        }
                    }

                }, {
                    Timber.e(it)
                    lastSearchedMembersVisibility.postValue(false)
                    lastSearchedMembers.postValue(listOf())
                })
        )
    }

    fun refreshLastSearchedMembersSortedByRank() {
        compositeDisposable.add(codewarsRepository.getLastSearchedMembersSortedByRank(LAST_SEARCHED_MEMBERS_LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when(it.code) {
                        RepositoryResponse.ResponseCode.SUCCESS -> {
                            lastSearchedMembers.postValue(it.data)
                            lastSearchedMembersVisibility.postValue(true)
                        }
                        RepositoryResponse.ResponseCode.NO_DATA -> {
                            lastSearchedMembersVisibility.postValue(false)
                            lastSearchedMembers.postValue(it.data)
                        }
                        RepositoryResponse.ResponseCode.ERROR,
                        RepositoryResponse.ResponseCode.SERVER_ERROR -> {
                            //todo handle error in UI
                            lastSearchedMembersVisibility.postValue(false)
                            lastSearchedMembers.postValue(it.data)
                        }
                        else -> {
                            //todo handle error in UI
                            lastSearchedMembersVisibility.postValue(false)
                            lastSearchedMembers.postValue(it.data)
                        }
                    }

                }, {
                    Timber.e(it)
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
                        .subscribe({
                                    when(it.code) {
                                        RepositoryResponse.ResponseCode.SUCCESS -> {
                                            foundedMember.postValue(it.data?.userName)
                                        }
                                        RepositoryResponse.ResponseCode.NO_DATA -> {
                                            noFoundMemberMsgVisibility.postValue(true)
                                        }
                                        RepositoryResponse.ResponseCode.ERROR,
                                        RepositoryResponse.ResponseCode.SERVER_ERROR -> {
                                            //todo handle error in UI
                                        }
                                        else -> {
                                            //todo handle error in UI
                                        }
                                    }
                                    loading.postValue(false)
                                },
                                {
                                    //todo handle error in UI
                                    Timber.e(it)
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