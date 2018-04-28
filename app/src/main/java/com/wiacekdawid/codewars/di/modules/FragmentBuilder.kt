package com.wiacekdawid.codewars.di.modules

import android.support.v4.app.Fragment
import com.wiacekdawid.codewars.di.components.MembersListFragmentSubcomponent
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
}
