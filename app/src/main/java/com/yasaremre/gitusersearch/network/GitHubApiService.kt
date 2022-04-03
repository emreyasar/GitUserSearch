package com.yasaremre.gitusersearch.network

import com.yasaremre.gitusersearch.network.model.SearchUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

    @GET("search/users?")
    suspend fun searchUsers(@Query("q") searchKey: String?): Response<SearchUsersResponse>

}