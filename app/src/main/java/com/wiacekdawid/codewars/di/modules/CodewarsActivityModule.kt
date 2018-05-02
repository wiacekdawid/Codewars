package com.wiacekdawid.codewars.di.modules

import com.wiacekdawid.codewars.di.components.ChallengesListFragmentSubcomponent
import com.wiacekdawid.codewars.di.components.MembersListFragmentSubcomponent
import dagger.Module

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Module(subcomponents = [MembersListFragmentSubcomponent::class, ChallengesListFragmentSubcomponent::class])
class CodewarsActivityModule