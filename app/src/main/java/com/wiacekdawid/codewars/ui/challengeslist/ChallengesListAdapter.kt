package com.wiacekdawid.codewars.ui.challengeslist

import android.arch.paging.PagedListAdapter
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wiacekdawid.codewars.R
import com.wiacekdawid.codewars.data.local.CompletedChallenge
import com.wiacekdawid.codewars.databinding.ItemChallengesListBinding

/**
 * Created by dawidwiacek on 01/05/2018.
 */

class ChallengesListAdapter: PagedListAdapter<CompletedChallenge, ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_challenges_list, parent, false)
        val binding = ItemChallengesListBinding.bind(view)
        val completedChallengeItemViewModel = CompletedChallengeItemViewModel()
        binding.viewModel = completedChallengeItemViewModel
        return ViewHolder(view, binding, completedChallengeItemViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CompletedChallenge>() {
            override fun areItemsTheSame(oldItem: CompletedChallenge, newItem: CompletedChallenge): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CompletedChallenge, newItem: CompletedChallenge): Boolean =
                    oldItem == newItem
        }
    }
}

class ViewHolder(view: View,
                 private val viewDataBinding: ViewDataBinding,
                 private val completedChallengeItemViewModel:
                 CompletedChallengeItemViewModel): RecyclerView.ViewHolder(viewDataBinding.root) {
    internal fun setItem(completedChallage: CompletedChallenge?) {
        completedChallengeItemViewModel.setItem(completedChallage)
        viewDataBinding.executePendingBindings()
    }
}
