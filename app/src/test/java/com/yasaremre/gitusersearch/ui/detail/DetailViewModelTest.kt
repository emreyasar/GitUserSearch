package com.yasaremre.gitusersearch.ui.detail

import androidx.lifecycle.MutableLiveData
import com.yasaremre.gitusersearch.BaseUnitTest
import com.yasaremre.gitusersearch.network.GitHubApiService
import com.yasaremre.gitusersearch.network.model.GitHubUserDTO
import io.mockk.*
import org.junit.Test
import retrofit2.Response

class DetailViewModelTest : BaseUnitTest() {

    lateinit var viewModel: DetailViewModel

    lateinit var mockGitHubApiService: GitHubApiService

    override fun setUp() {
        super.setUp()
        mockGitHubApiService = mockk(relaxed = true)
        viewModel = spyk(DetailViewModel(mockGitHubApiService))
    }

    @Test
    fun `fetches user details and set value`() {
        val mockUser = mockk<MutableLiveData<GitHubUserDTO>>(relaxed = true)
        val mockResponse = mockk<Response<GitHubUserDTO>>()
        every { mockResponse.body() } returns GitHubUserDTO()
        coEvery { mockGitHubApiService.getUserDetail(any()) } returns mockResponse
        every { viewModel.user } returns mockUser

        viewModel.fetchUserDetails("username1")

        coVerify { mockGitHubApiService.getUserDetail("username1") }
        coVerify { mockUser.postValue(any()) }
    }

}