package com.yasaremre.gitusersearch.ui.search.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yasaremre.gitusersearch.databinding.ListItemUserBinding
import com.yasaremre.gitusersearch.extension.layoutInflater

class UserListViewHolder(val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): UserListViewHolder {
            val layoutInflater = parent.context.layoutInflater
            val binding = ListItemUserBinding.inflate(layoutInflater, parent, false)
            return UserListViewHolder(binding)
        }
    }
}