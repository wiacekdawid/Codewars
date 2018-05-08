package com.wiacekdawid.codewars.ui.challengeslist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class ChallengesListViewModel(var username: String, val codewarsRepository: CodewarsRepository): ViewModel() {
    var completedChallenges: LiveData<PagedList<CompletedChallenge>>? = null
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var noMoreData = false
    var lastLoadedPage = 0
    var boundaryCallback: CompletedChallengesBoundaryCallback? = null

    init {
        boundaryCallback = CompletedChallengesBoundaryCallback(codewarsRepository, this)
        completedChallenges =
                LivePagedListBuilder<Int, CompletedChallenge>(codewarsRepository.getCompletedChallenges(username), 20)
                        .setBoundaryCallback(boundaryCallback)
                        .setFetchExecutor(BackgroundThreadExecutor())
                        .build()
    }

    fun onRefresh(forceUpdate: Boolean) {
        if(isLoading.value != true && (!noMoreData || forceUpdate)) {
            isLoading.postValue(true)
            codewarsRepository.loadMoreCompletedChallenges(username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        isLoading.postValue(false)
                        if(it.data == null || it.data.isEmpty()) {
                            noMoreData = true
                            boundaryCallback = null
                        }
                    }, {
                        isLoading.postValue(false)
                        Timber.e(it)
                    })

        }
    }

    internal inner class UiThreadExecutor : Executor {
        private val mHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mHandler.post(command)
        }
    }

    internal inner class BackgroundThreadExecutor : Executor {
        private val executorService = Executors.newFixedThreadPool(3)

        override fun execute(command: Runnable) {
            executorService.execute(command)
        }
    }
}