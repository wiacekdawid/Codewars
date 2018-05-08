package com.wiacekdawid.codewars.ui.challengeslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.style.TtsSpan
import android.view.*
import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.databinding.FragmentChallengesListBinding
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class ChallengesListFragment: Fragment() {

    companion object {
        const val MEMBER_ID = "userName"
    }

    @Inject
    lateinit var challengesListViewModelFactory: ChallengesListViewModelFactory

    lateinit var challengesListViewModel: ChallengesListViewModel

    private var fragmentChallengesListBinding: FragmentChallengesListBinding? = null
    private var challengesListAdapter: ChallengesListAdapter? = null
    var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        challengesListViewModel = ViewModelProviders.of(this, challengesListViewModelFactory).get(ChallengesListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        linearLayoutManager = LinearLayoutManager(activity)
        fragmentChallengesListBinding = FragmentChallengesListBinding.inflate(inflater, container, false)
        fragmentChallengesListBinding?.setLifecycleOwner(this)
        fragmentChallengesListBinding?.viewModel = challengesListViewModel
        setupRecyclerView()
        return fragmentChallengesListBinding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupRecyclerView() {
        val recyclerView = fragmentChallengesListBinding?.fclRvChallenges
        challengesListAdapter = ChallengesListAdapter()
        challengesListViewModel.completedChallenges?.observe(this,
                Observer<PagedList<CompletedChallenge>> {
                    challengesListAdapter?.submitList(it)
                })

        recyclerView?.adapter = challengesListAdapter
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.addItemDecoration(
                object : DividerItemDecoration(activity, linearLayoutManager?.orientation) {
                    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                        val position = parent!!.getChildAdapterPosition(view)
                        // hide the divider for the last child
                        if (position == parent.adapter.itemCount - 1) {
                            outRect.setEmpty()
                        } else {
                            super.getItemOffsets(outRect, view, parent, state)
                        }
                    }
                }
        )
    }
}