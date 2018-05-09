package com.wiacekdawid.codewars.ui.challengedetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by dawidwiacek on 08/05/2018.
 */

class ChallengeDetailsViewModel(var username: String,
                                var challengeId: String,
                                var isCompletedChallenge: Boolean,
                                val codewarsRepository: CodewarsRepository): ViewModel() {
    fun loadData() {
        if(isCompletedChallenge) {
            codewarsRepository.getCompletedChallenge(challengeId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                userName.postValue(it.userName)
                                name.postValue(it.name)
                                id.postValue(it.id)
                            },
                            {
                                Timber.e(it)
                            })
        }
        else {
            codewarsRepository.getAuthoredChallenge(challengeId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                userName.postValue(it.userName)
                                name.postValue(it.name)
                                id.postValue(it.id)
                            },
                            {
                                Timber.e(it)
                            })
        }
    }
    var userName: MutableLiveData<String> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var id: MutableLiveData<String> = MutableLiveData()

}