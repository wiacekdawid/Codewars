package com.wiacekdawid.codewars.ui.memberslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.databinding.FragmentMembersListBinding
import com.wiacekdawid.codewars.ui.AttachedCodewarsActivity
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by dawidwiacek on 28/04/2018.
 */

class MembersListFragment: Fragment() {

    @Inject
    lateinit var membersListViewModelFactory: MembersListViewModelFactory

    lateinit var membersListViewModel: MembersListViewModel

    private var fragmentMembersListBinding: FragmentMembersListBinding? = null
    private var lastSearchedMembersListAdapter: LastSearchedMembersListAdapter? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        membersListViewModel = ViewModelProviders.of(this, membersListViewModelFactory).get(MembersListViewModel::class.java)
        membersListViewModel.refreshLastSearchedMembers()
        membersListViewModel.selectMember.observe(this, observer = Observer() {
            it?.let { (activity as AttachedCodewarsActivity).openChallenges(it)}
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentMembersListBinding = FragmentMembersListBinding.inflate(inflater, container, false)
        fragmentMembersListBinding?.viewmodel = membersListViewModel
        fragmentMembersListBinding?.setLifecycleOwner(this)
        setupList()
        return fragmentMembersListBinding?.root
    }

    private fun setupList() {
        val listView = fragmentMembersListBinding?.fmlLvLastSearchedUsers
        lastSearchedMembersListAdapter = LastSearchedMembersListAdapter()
        lastSearchedMembersListAdapter?.submitList(membersListViewModel.lastSearchedMembers?.value ?: arrayListOf())
        membersListViewModel.lastSearchedMembers.observe(this,
                Observer<List<Member>> {
                    lastSearchedMembersListAdapter?.submitList(it ?: arrayListOf())
                })

        listView?.adapter = lastSearchedMembersListAdapter
    }
}