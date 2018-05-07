package com.wiacekdawid.codewars.ui.memberslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.wiacekdawid.codewars.data.local.Member
import com.wiacekdawid.codewars.databinding.ItemLastSearchedMembersListBinding
import com.wiacekdawid.codewars.R

/**
 * Created by dawidwiacek on 06/05/2018.
 */

class LastSearchedMembersListAdapter: BaseAdapter() {

    var list: List<Member> = arrayListOf()

    fun submitList(list: List<Member>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(p2.context)
        val view = layoutInflater.inflate(R.layout.item_last_searched_members_list, p2, false)
        var binding = ItemLastSearchedMembersListBinding.bind(view)
        val lastSearchedMemberItemViewModel = LastSearchedMemberItemViewModel()
        lastSearchedMemberItemViewModel.setItem(list[p0])
        binding.viewModel = lastSearchedMemberItemViewModel
        notifyDataSetChanged()
        return binding.root
    }

    override fun getItem(p0: Int) = list[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getCount() = list.size

}