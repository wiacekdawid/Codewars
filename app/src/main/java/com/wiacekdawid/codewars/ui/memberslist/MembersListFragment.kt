package com.wiacekdawid.codewars.ui.memberslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.wiacekdawid.codewars.R
import com.wiacekdawid.codewars.data.local.Member
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

    private var fragmentMembersListBinding: FragmentMembersListBinding? = null
    private var lastSearchedMembersListAdapter: LastSearchedMembersListAdapter? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        membersListViewModel = ViewModelProviders.of(this, membersListViewModelFactory).get(MembersListViewModel::class.java)
        membersListViewModel.refreshLastSearchedMembersSortedByDate()
        membersListViewModel.selectMember.observe(this, observer = Observer {
            it?.let { (activity as AttachedCodewarsActivity).openChallenges(it)}
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentMembersListBinding = FragmentMembersListBinding.inflate(inflater, container, false)
        fragmentMembersListBinding?.setLifecycleOwner(this)
        setupList()
        return fragmentMembersListBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentMembersListBinding?.viewmodel = membersListViewModel
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.members_sort_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.msm_i_sort_by_date -> {
                membersListViewModel.refreshLastSearchedMembersSortedByDate()
                return true
            }
            R.id.msm_i_sort_by_rank -> {
                membersListViewModel.refreshLastSearchedMembersSortedByRank()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupList() {
        val listView = fragmentMembersListBinding?.fmlLvLastSearchedUsers
        lastSearchedMembersListAdapter = LastSearchedMembersListAdapter(attachedCodewarsActivity = (activity as AttachedCodewarsActivity))
        lastSearchedMembersListAdapter?.submitList(membersListViewModel.lastSearchedMembers?.value ?: arrayListOf())
        membersListViewModel.lastSearchedMembers.observe(this,
                Observer<List<Member>> {
                    lastSearchedMembersListAdapter?.submitList(it ?: arrayListOf())
                })
        listView?.adapter = lastSearchedMembersListAdapter
    }
}