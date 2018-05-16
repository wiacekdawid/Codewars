package com.wiacekdawid.codewars.ui.challengeslist

import android.arch.paging.PagedList

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