package com.wiacekdawid.codewars.ui.memberslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wiacekdawid.codewars.databinding.FragmentMembersListBinding
import com.wiacekdawid.codewars.ui.AttachedCodewarsActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by dawidwiacek on 28/04/2018.
 */

class MembersListFragment: Fragment() {

    @Inject
    lateinit var membersListViewModelFactory: MembersListViewModelFactory

    lateinit var membersListViewModel: MembersListViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        membersListViewModel = ViewModelProviders.of(this, membersListViewModelFactory).get(MembersListViewModel::class.java)

        membersListViewModel.selectMember.observe(this, observer = Observer<String>() {
            it?.let { (activity as AttachedCodewarsActivity).openChallenges(it)}
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMembersListBinding.inflate(inflater, container, false)
        binding.viewmodel = membersListViewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }
}