package com.wiacekdawid.codewars.di.modules

import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.di.scopes.FragmentScope
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListFragment
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Module
class ChallengesListFragmentModule {
    @FragmentScope
    @Provides
    internal fun provideChallengesListViewModelFactory(challengesListFragment: ChallengesListFragment, codewarsRepository: CodewarsRepository) =
            ChallengesListViewModelFactory(challengesListFragment.arguments?.getString(ChallengesListFragment.MEMBER_ID)
                    ?: "", codewarsRepository)
}