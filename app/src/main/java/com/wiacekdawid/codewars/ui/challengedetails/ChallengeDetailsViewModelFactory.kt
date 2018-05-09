package com.wiacekdawid.codewars.ui.challengedetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wiacekdawid.codewars.data.repository.CodewarsRepository

/**
 * Created by dawidwiacek on 08/05/2018.
 */

class ChallengesDetailsViewModelFactory(private val userName: String,
                                        private val challengeId: String,
                                        private val isCompletedChallenge: Boolean,
                                        private val codewarsRepository: CodewarsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(String::class.java, String::class.java, Boolean::class.java, CodewarsRepository::class.java)
                .newInstance(userName, challengeId, isCompletedChallenge, codewarsRepository)
    }
}