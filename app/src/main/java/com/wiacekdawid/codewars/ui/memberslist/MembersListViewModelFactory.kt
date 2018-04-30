package com.wiacekdawid.codewars.ui.memberslist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wiacekdawid.codewars.data.repository.CodewarsRepository

/**
 * Created by dawidwiacek on 29/04/2018.
 */

class MembersListViewModelFactory (private var codewarsRepository: CodewarsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CodewarsRepository::class.java)
                .newInstance(codewarsRepository)
    }
}