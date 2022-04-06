package com.yasaremre.gitusersearch.ui.search.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yasaremre.gitusersearch.network.model.GitHubUserDTO
import com.yasaremre.gitusersearch.ui.search.listener.ViewHolderClickListener
import com.yasaremre.gitusersearch.ui.search.viewholder.UserListViewHolder

class UserListRecyclerViewAdapter(
    private val viewHolderClickListener: ViewHolderClickListener<RecyclerView.ViewHolder>?,
    diffCallback: DiffUtil.ItemCallback<GitHubUserDTO>
) :
    PagingDataAdapter<GitHubUserDTO, RecyclerView.ViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        (holder as UserListViewHolder).binding.user = user
        viewHolderClickListener?.setTo(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserListViewHolder.create(parent)
    }

}