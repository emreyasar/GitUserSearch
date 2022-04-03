package com.yasaremre.gitusersearch.network.model

data class SearchUsersResponse(
    val totalCount: Int? = null,
    val incompleteResults: Boolean? = null,
    val items: ArrayList<GitHubUserDTO>
)