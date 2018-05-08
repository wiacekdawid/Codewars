package com.wiacekdawid.codewars.ui.challengeslist

import android.arch.paging.PagedList
import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.data.remote.api.CompletedChallengeDto
import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class ChallengesBoundaryCallback(val challengesListViewModel: ChallengesListViewModel):
        PagedList.BoundaryCallback<Challenge>() {

    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
        challengesListViewModel.onRefresh(false)
    }

    override fun onZeroItemsLoaded() {
        challengesListViewModel.onRefresh(true)
    }
}