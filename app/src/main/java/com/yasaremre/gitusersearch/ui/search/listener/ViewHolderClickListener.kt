package com.yasaremre.gitusersearch.ui.search.listener

import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolderClickListener<VH : RecyclerView.ViewHolder?> {
    abstract fun onClick(holder: VH)

    open fun onLongClick(holder: VH): Boolean {
        return true
    }

    fun setTo(holder: VH) {
        if (holder != null) {
            holder.itemView.setOnClickListener { onClick(holder) }
            holder.itemView.setOnLongClickListener { onLongClick(holder) }
        }

    }
}