package com.yasaremre.gitusersearch.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.yasaremre.gitusersearch.core.BaseViewModel
import com.yasaremre.gitusersearch.network.model.GitHubUserDTO
import com.yasaremre.gitusersearch.ui.search.paging.SearchListPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchListPagingRepository
) : BaseViewModel() {

    val searchText = MutableLiveData<String>()

    private var currentSearchQuery: String? = null

    private var currentSearchResult: Flow<PagingData<GitHubUserDTO>>? = null

    /*
    * If the same query sent as the last search operation, return the previous existing one results.
    * */
    fun searchUsers(query: String): Flow<PagingData<GitHubUserDTO>> {
        val lastResult = currentSearchResult
        if (query == currentSearchQuery && lastResult != null) {
            return lastResult
        }
        currentSearchQuery = query
        val newResult = repository.searchUsers(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}