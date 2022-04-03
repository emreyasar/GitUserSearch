package com.yasaremre.gitusersearch.network.model

import com.squareup.moshi.Json

data class GitHubUserDTO(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "login") var login: String? = null,
    @Json(name = "avatar_url") val avatar_url: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "company") val company: String? = null,
    @Json(name = "blog") val blog: String? = null,
    @Json(name = "location") val location: String? = null,
    @Json(name = "email") val email: String? = null,
    @Json(name = "bio") val bio: String? = null,
    @Json(name = "followers") val followers: String? = null,
    @Json(name = "following") val following: String? = null
)