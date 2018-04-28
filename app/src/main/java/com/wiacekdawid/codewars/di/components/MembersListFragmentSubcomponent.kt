package com.wiacekdawid.codewars.di.components

import com.wiacekdawid.codewars.di.modules.MembersListFragmentModule
import com.wiacekdawid.codewars.di.scopes.FragmentScope
import com.wiacekdawid.codewars.ui.memberslist.MembersListFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@FragmentScope
@Subcomponent(modules = [(MembersListFragmentModule::class)])
interface MembersListFragmentSubcomponent : AndroidInjector<MembersListFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MembersListFragment>()
}