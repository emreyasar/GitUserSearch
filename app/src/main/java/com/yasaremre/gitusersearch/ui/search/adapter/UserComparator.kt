package com.yasaremre.gitusersearch.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.yasaremre.gitusersearch.network.model.GitHubUserDTO

object UserComparator : DiffUtil.ItemCallback<GitHubUserDTO>() {
    override fun areItemsTheSame(oldItem: GitHubUserDTO, newItem: GitHubUserDTO): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GitHubUserDTO, newItem: GitHubUserDTO): Boolean {
        return oldItem == newItem
    }
}
