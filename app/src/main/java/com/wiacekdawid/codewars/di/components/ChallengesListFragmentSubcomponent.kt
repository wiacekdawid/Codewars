package com.wiacekdawid.codewars.di.components

import com.wiacekdawid.codewars.di.modules.ChallengesListFragmentModule
import com.wiacekdawid.codewars.di.scopes.FragmentScope
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@FragmentScope
@Subcomponent(modules = [(ChallengesListFragmentModule::class)])
interface ChallengesListFragmentSubcomponent : AndroidInjector<ChallengesListFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ChallengesListFragment>()
}