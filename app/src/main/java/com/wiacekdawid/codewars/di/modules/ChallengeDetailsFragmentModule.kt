package com.wiacekdawid.codewars.di.modules

import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.di.scopes.FragmentScope
import com.wiacekdawid.codewars.ui.challengedetails.ChallengeDetailsFragment
import com.wiacekdawid.codewars.ui.challengedetails.ChallengesDetailsViewModelFactory
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListFragment
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by dawidwiacek on 08/05/2018.
 */

@Module
class ChallengeDetailsFragmentModule {
    @FragmentScope
    @Provides
    internal fun provideChallengeDetailsViewModelFactory(challengeDetailsFragment: ChallengeDetailsFragment, codewarsRepository: CodewarsRepository) =
            ChallengesDetailsViewModelFactory(challengeDetailsFragment.arguments?.getString(ChallengeDetailsFragment.USER_NAME) ?: "",
                    challengeDetailsFragment.arguments?.getString(ChallengeDetailsFragment.CHALLENGE_ID) ?: "",
                    challengeDetailsFragment.arguments?.getBoolean(ChallengeDetailsFragment.COMPLETED_CHALLENGE) ?: true,
                    codewarsRepository)
}