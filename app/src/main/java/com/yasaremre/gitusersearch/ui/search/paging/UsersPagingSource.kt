package com.yasaremre.gitusersearch.ui.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yasaremre.gitusersearch.network.GitHubApiService
import com.yasaremre.gitusersearch.network.model.GitHubUserDTO
import retrofit2.HttpException
import java.io.IOException

class UsersPagingSource(private val apiService: GitHubApiService, private val searchText: String? = null) : PagingSource<Int, GitHubUserDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubUserDTO> {
        // for first case it will be null, then we can pass some default value, in our case it's 1
        // for the first page, loadSize will be 3 * pageSize. To override this, set initialLoadSize from PagingConfig.
        val page = params.key ?: 1
        return try {
            val response = apiService.searchUsers(searchText, page, params.loadSize)
            if (response.isSuccessful) {
                var items = listOf<GitHubUserDTO>()
                response.body()?.let {
                    items = it.items
                }
                LoadResult.Page(
                    items,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (items.isEmpty() || items.size < params.loadSize) null else page + 1
                )
            } else {
                /*
                * TODO E.Y. : whole errorBody given to exception object as message in current implementation
                *  cast it to GitHubError object and pass just messge field.
                * */
                LoadResult.Error(Exception(response.errorBody()?.string()))
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitHubUserDTO>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}