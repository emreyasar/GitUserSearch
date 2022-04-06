package com.yasaremre.gitusersearch.network.mock

import com.yasaremre.gitusersearch.network.GitHubApiService
import com.yasaremre.gitusersearch.network.model.GitHubUserDTO
import com.yasaremre.gitusersearch.network.model.SearchUsersResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import kotlin.math.min

/*
 * This class was written to mock the network service to test the paging3 library.
 * https://developer.android.com/topic/libraries/architecture/paging/test
 */

class MockGithubApiService: GitHubApiService {

    private val users : MutableList<GitHubUserDTO> = arrayListOf()

    fun addMockUser(mockUser: GitHubUserDTO) {
        users.add(mockUser)
    }

    override suspend fun searchUsers(
        searchKey: String?,
        page: Int?,
        size: Int?
    ): Response<SearchUsersResponse> {
        if (page != null && size != null) {
            val startPos = (page - 1) * size
            val endPos = page * size
            var sublist = listOf<GitHubUserDTO>()
            if (startPos < users.size) {
                sublist = users.subList(startPos, min(users.size, endPos)).toList()
            }

            return Response.success(SearchUsersResponse(totalCount = sublist.size, items = sublist))
        }
        return Response.error(403, "{\"key\":[\"page_or_size_is_null\"]}".toResponseBody("application/json".toMediaTypeOrNull()))
    }

    override suspend fun getUserDetail(username: String?): Response<GitHubUserDTO> {
        return Response.success(GitHubUserDTO(name = username))
    }


}