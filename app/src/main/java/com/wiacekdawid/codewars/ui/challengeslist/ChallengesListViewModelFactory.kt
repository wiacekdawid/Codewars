package com.wiacekdawid.codewars.ui.challengeslist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wiacekdawid.codewars.data.repository.CodewarsRepository

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class ChallengesListViewModelFactory(private val memberId: String, private val codewarsRepository: CodewarsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(String::class.java, CodewarsRepository::class.java)
                .newInstance(memberId, codewarsRepository)
    }
}