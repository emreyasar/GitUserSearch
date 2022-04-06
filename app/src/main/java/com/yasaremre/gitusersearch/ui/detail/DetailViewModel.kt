package com.yasaremre.gitusersearch.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yasaremre.gitusersearch.core.BaseViewModel
import com.yasaremre.gitusersearch.core.Dispatchers
import com.yasaremre.gitusersearch.network.GitHubApiService
import com.yasaremre.gitusersearch.network.model.GitHubUserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val gitHubApiService: GitHubApiService
) : BaseViewModel() {

    val user = MutableLiveData<GitHubUserDTO>()

    fun fetchUserDetails(username: String) {
        viewModelScope.launch(Dispatchers.io()) {
            val response = gitHubApiService.getUserDetail(username)
            response.body()?.let {
                user.postValue(it)
            }
        }
    }


}