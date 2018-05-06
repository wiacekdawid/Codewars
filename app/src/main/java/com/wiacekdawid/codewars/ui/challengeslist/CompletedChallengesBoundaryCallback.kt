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

class CompletedChallengesBoundaryCallback(val codewarsRepository: CodewarsRepository,
                                          val challengesListViewModel: ChallengesListViewModel):
        PagedList.BoundaryCallback<CompletedChallenge>() {

    override fun onItemAtEndLoaded(itemAtEnd: CompletedChallenge) {
        /*codewarsRepository.fetchCompletedChallenges(challengesListViewModel.username)
                .subscribeOn(Schedulers.io())
                .map {
                    var listOfCompletedChallenge: MutableList<CompletedChallenge> = arrayListOf()
                    for(completedChallengeDto in (it?.body()?.data as List<CompletedChallengeDto>)) {
                        listOfCompletedChallenge.add(CompletedChallenge(completedChallengeDto.id,
                                completedChallengeDto.name, challengesListViewModel.username))
                    }
                    listOfCompletedChallenge
                }
                .doAfterSuccess {
                    codewarsRepository.addCompletedChallengesToDB(it)
                            .subscribeOn(Schedulers.io())
                            .subscribe { Timber.i("success") }
                }
                .subscribe({
                    Timber.i("success")
                }, {
                    Timber.e(it)
                })*/

    }

    override fun onZeroItemsLoaded() {
        /*codewarsRepository.fetchCompletedChallenges(challengesListViewModel.username)
                .subscribeOn(Schedulers.io())
                .map {
                    var listOfCompletedChallenge: MutableList<CompletedChallenge> = arrayListOf()
                    for(completedChallengeDto in (it?.body()?.data as List<CompletedChallengeDto>)) {
                        listOfCompletedChallenge.add(CompletedChallenge(completedChallengeDto.id,
                                completedChallengeDto.name, challengesListViewModel.username))
                    }
                    listOfCompletedChallenge
                }
                .doAfterSuccess {
                    codewarsRepository.addCompletedChallengesToDB(it)
                            .subscribeOn(Schedulers.io())
                            .subscribe { Timber.i("success") }
                }
                .subscribe({
                    Timber.i("success")
                }, {
                    Timber.e(it)
                })*/
    }
}