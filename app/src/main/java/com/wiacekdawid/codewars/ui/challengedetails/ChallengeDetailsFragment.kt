package com.wiacekdawid.codewars.ui.challengedetails

import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.wiacekdawid.codewars.R
import com.wiacekdawid.codewars.databinding.FragmentChallengeDetailsBinding
import com.wiacekdawid.codewars.databinding.FragmentChallengesListBinding
import com.wiacekdawid.codewars.ui.challengeslist.Challenge
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListAdapter
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListViewModel
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by dawidwiacek on 08/05/2018.
 */

class ChallengeDetailsFragment: Fragment() {

    companion object {
        const val USER_NAME = "userName"
        const val CHALLENGE_ID = "challengeId"
        const val COMPLETED_CHALLENGE = "completedChallenge"
    }

    @Inject
    lateinit var challengeDetailsViewModelFactory: ChallengesDetailsViewModelFactory

    lateinit var challengeDetailsViewModel: ChallengeDetailsViewModel

    private var fragmentChallengeDetailsBinding: FragmentChallengeDetailsBinding? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        challengeDetailsViewModel = ViewModelProviders.of(this, challengeDetailsViewModelFactory).get(ChallengeDetailsViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        challengeDetailsViewModel.loadData()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentChallengeDetailsBinding = FragmentChallengeDetailsBinding.inflate(inflater, container, false)
        fragmentChallengeDetailsBinding?.setLifecycleOwner(this)
        fragmentChallengeDetailsBinding?.challengeDetailsViewModel = challengeDetailsViewModel
        return fragmentChallengeDetailsBinding?.root
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        var menuItem = menu?.findItem(R.id.msm_i_sort)
        menuItem?.isVisible = false
    }
}