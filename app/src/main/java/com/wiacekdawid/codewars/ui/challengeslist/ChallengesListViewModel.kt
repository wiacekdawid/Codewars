package com.wiacekdawid.codewars.ui.challengeslist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.view.MenuItem
import com.wiacekdawid.codewars.R
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class ChallengesListViewModel(var username: String, val codewarsRepository: CodewarsRepository): ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    var challenges: LiveData<PagedList<Challenge>>? = null
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var noMoreCompletedChallengeData = false
    var noMoreAuthoredChallengeData = false
    var loadCompletedChallenges = true
    var boundaryCallback: ChallengesBoundaryCallback? = null
    var refreshList = SingleLiveEvent<Boolean>()

    init {
        boundaryCallback = ChallengesBoundaryCallback(this)
        codewarsRepository.resetPaginationData()
        challenges = LivePagedListBuilder<Int, Challenge>(codewarsRepository
                        .getCompletedChallenges(username).map {
                        Challenge(id = it.id, name = it.name ?: "", userName = it.userName, isCompleted = true) }, 20)
                        .setBoundaryCallback(boundaryCallback)
                        .setFetchExecutor(BackgroundThreadExecutor())
                        .build()
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun onRefresh(forceUpdate: Boolean) {
        if(isLoading.value != true) {
            if(loadCompletedChallenges) {
                if(!noMoreCompletedChallengeData || forceUpdate) {
                    if(forceUpdate) codewarsRepository.resetPaginationData()
                    isLoading.postValue(true)
                    compositeDisposable.add(codewarsRepository.loadMoreCompletedChallenges(username)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                isLoading.postValue(false)
                                if (it.data == null || it.data.isEmpty()) {
                                    noMoreCompletedChallengeData = true
                                    boundaryCallback = null
                                }
                            }, {
                                isLoading.postValue(false)
                                Timber.e(it)
                            })
                    )
                }
            }
            else {
                if(!noMoreAuthoredChallengeData || forceUpdate) {
                    isLoading.postValue(true)
                    compositeDisposable.add(codewarsRepository.refreshAuthoredChallenges(username)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                isLoading.postValue(false)
                                noMoreAuthoredChallengeData = true
                                boundaryCallback = null
                            }, {
                                isLoading.postValue(false)
                                Timber.e(it)
                            })
                    )
                }
            }

        }
    }

    fun onNavigationClick(item: MenuItem): Boolean {
        if(isLoading.value != true) {
            when (item.itemId) {
                R.id.clbnm_i_authored -> {
                    loadCompletedChallenges = false
                    challenges = LivePagedListBuilder<Int, Challenge>(codewarsRepository
                            .getAuthoredChallenges(username).map {
                                Challenge(id = it.id, name = it.name
                                        ?: "", userName = it.userName, isCompleted = true)
                            }, 20)
                            .setBoundaryCallback(boundaryCallback)
                            .setFetchExecutor(BackgroundThreadExecutor())
                            .build()
                    refreshList.postValue(true)
                    onRefresh(false)
                    return true
                }
                R.id.clbnm_i_completed -> {
                    loadCompletedChallenges = true
                    challenges =
                            LivePagedListBuilder<Int, Challenge>(codewarsRepository
                                    .getCompletedChallenges(username).map {
                                        Challenge(id = it.id, name = it.name
                                                ?: "", userName = it.userName, isCompleted = false)
                                    }, 20)
                                    .setBoundaryCallback(boundaryCallback)
                                    .setFetchExecutor(BackgroundThreadExecutor())
                                    .build()
                    refreshList.postValue(true)
                    onRefresh(false)
                    return true
                }
            }
        }
        return false
    }

    internal inner class BackgroundThreadExecutor : Executor {
        private val executorService = Executors.newFixedThreadPool(3)

        override fun execute(command: Runnable) {
            executorService.execute(command)
        }
    }
}