package com.wiacekdawid.codewars.di.modules

import com.wiacekdawid.codewars.data.repository.CodewarsRepository
import com.wiacekdawid.codewars.di.scopes.FragmentScope
import com.wiacekdawid.codewars.ui.memberslist.MembersListViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Module
class MembersListFragmentModule {
    @FragmentScope
    @Provides
    internal fun provideMembersListViewModelFactory(codewarsRepository: CodewarsRepository) =
            MembersListViewModelFactory(codewarsRepository)
}