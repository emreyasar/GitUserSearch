package com.yasaremre.gitusersearch.network

import com.yasaremre.gitusersearch.network.model.GitHubUserDTO
import com.yasaremre.gitusersearch.network.model.SearchUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("search/users?")
    suspend fun searchUsers(@Query("q") searchKey: String?,
                            @Query("page") page: Int? = 1,
                            @Query("per_page") size: Int? = 30): Response<SearchUsersResponse>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String?): Response<GitHubUserDTO>
}