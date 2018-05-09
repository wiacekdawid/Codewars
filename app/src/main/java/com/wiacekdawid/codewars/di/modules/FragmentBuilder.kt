package com.wiacekdawid.codewars.di.modules

import android.support.v4.app.Fragment
import com.wiacekdawid.codewars.di.components.ChallengeDetailsFragmentSubcomponent
import com.wiacekdawid.codewars.di.components.ChallengesListFragmentSubcomponent
import com.wiacekdawid.codewars.di.components.MembersListFragmentSubcomponent
import com.wiacekdawid.codewars.ui.challengedetails.ChallengeDetailsFragment
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListFragment
import com.wiacekdawid.codewars.ui.memberslist.MembersListFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Module
abstract class FragmentBuilder {

    @Binds
    @IntoMap
    @FragmentKey(MembersListFragment::class)
    internal abstract fun bindMembersListFragment(builder: MembersListFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(ChallengesListFragment::class)
    internal abstract fun bindChallengesListFragment(builder: ChallengesListFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(ChallengeDetailsFragment::class)
    internal abstract fun bindChallengeDetailsFragment(builder: ChallengeDetailsFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

}
