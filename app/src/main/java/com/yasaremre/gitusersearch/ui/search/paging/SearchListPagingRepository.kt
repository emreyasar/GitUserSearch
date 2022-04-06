package com.yasaremre.gitusersearch.ui.search.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yasaremre.gitusersearch.network.GitHubApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchListPagingRepository @Inject constructor(private val gitHubApiService: GitHubApiService) {

    companion object {
        private const val PAGE_SIZE = 30
    }

    fun searchUsers(query: String) = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { UsersPagingSource(gitHubApiService, query) }
    ).flow
}