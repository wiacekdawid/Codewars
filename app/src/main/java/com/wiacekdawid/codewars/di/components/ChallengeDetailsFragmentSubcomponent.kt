package com.wiacekdawid.codewars.di.components

import com.wiacekdawid.codewars.di.modules.ChallengeDetailsFragmentModule
import com.wiacekdawid.codewars.di.scopes.FragmentScope
import com.wiacekdawid.codewars.ui.challengedetails.ChallengeDetailsFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by dawidwiacek on 08/05/2018.
 */

@FragmentScope
@Subcomponent(modules = [(ChallengeDetailsFragmentModule::class)])
interface ChallengeDetailsFragmentSubcomponent : AndroidInjector<ChallengeDetailsFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ChallengeDetailsFragment>()
}