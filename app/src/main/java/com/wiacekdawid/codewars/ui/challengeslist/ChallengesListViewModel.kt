package com.wiacekdawid.codewars.ui.challengeslist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class ChallengesListViewModel(var username: String, codewarsRepository: CodewarsRepository): ViewModel() {
    var completedChallenges: LiveData<PagedList<CompletedChallenge>>? = null

    init {
        completedChallenges =
                LivePagedListBuilder<Int, CompletedChallenge>(codewarsRepository.getCompletedChallenges(username), 20)
                        .setBoundaryCallback(CompletedChallengesBoundaryCallback(codewarsRepository, this))
                        .setFetchExecutor(BackgroundThreadExecutor())
                        .build()
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