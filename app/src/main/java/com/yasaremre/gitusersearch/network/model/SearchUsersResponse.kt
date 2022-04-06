package com.yasaremre.gitusersearch.network.model

import com.squareup.moshi.Json

data class SearchUsersResponse(
    @Json(name = "totalCount")
    val totalCount: Int? = null,
    @Json(name = "incompleteResults")
    val incompleteResults: Boolean? = null,
    @Json(name = "items")
    val items: List<GitHubUserDTO> = listOf()
)