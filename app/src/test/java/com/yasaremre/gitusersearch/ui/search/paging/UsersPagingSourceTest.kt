package com.yasaremre.gitusersearch.ui.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Refresh
import com.yasaremre.gitusersearch.BaseUnitTest
import com.yasaremre.gitusersearch.network.mock.MockGithubApiService
import com.yasaremre.gitusersearch.network.model.GitHubUserDTO
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class UsersPagingSourceTest : BaseUnitTest() {

    private lateinit var pagingSource: UsersPagingSource

    private val mockItems = listOf(
        GitHubUserDTO(name = "Name0"),
        GitHubUserDTO(name = "Name1"),
        GitHubUserDTO(name = "Name2"),
        GitHubUserDTO(name = "Name3"),
        GitHubUserDTO(name = "Name4"),
        GitHubUserDTO(name = "Name5"),
        GitHubUserDTO(name = "Name6"),
        GitHubUserDTO(name = "Name7")
    )

    private val fakeApi = MockGithubApiService().apply {
        mockItems.forEach { user -> addMockUser(user) }
    }

    override fun setUp() {
        super.setUp()
        pagingSource = spyk(UsersPagingSource(fakeApi))
    }

    @Test
    fun `paging returns correct list and nextKey`() = runTest {
        assertEquals(
            expected = PagingSource.LoadResult.Page(
                data = listOf(mockItems[0], mockItems[1]),
                prevKey = null,
                nextKey = 2
            ),
            actual = pagingSource.load(
                Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ) as PagingSource.LoadResult.Page<Int, GitHubUserDTO>
        )
    }

    @Test
    fun `paging returns correct list, prevKey and nextKey`() = runTest {
        assertEquals(
            expected = PagingSource.LoadResult.Page(
                data = listOf(mockItems[2], mockItems[3]),
                prevKey = 1,
                nextKey = 3
            ),
            actual = pagingSource.load(
                Refresh(
                    key = 2,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ) as PagingSource.LoadResult.Page<Int, GitHubUserDTO>
        )
    }

    @Test
    fun `paging returns correct list, prevKey and nextKey when reach the last page`() = runTest {
        assertEquals(
            expected = PagingSource.LoadResult.Page(
                data = listOf(mockItems[6], mockItems[7]),
                prevKey = 3,
                nextKey = 5
            ),
            actual = pagingSource.load(
                Refresh(
                    key = 4,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ) as PagingSource.LoadResult.Page<Int, GitHubUserDTO>
        )
    }

    @Test
    fun `paging returns empty list, prevKey and nextKey when reach the end`() = runTest {
        assertEquals(
            expected = PagingSource.LoadResult.Page(
                data = listOf<GitHubUserDTO>(),
                prevKey = 4,
                nextKey = null
            ),
            actual = pagingSource.load(
                Refresh(
                    key = 5,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            ) as PagingSource.LoadResult.Page<Int, GitHubUserDTO>
        )
    }

    @Test
    fun `paging returns correct list when load size overflow`() = runTest {
        assertEquals(
            expected = PagingSource.LoadResult.Page<Int, GitHubUserDTO>(
                data = listOf(mockItems[0], mockItems[1], mockItems[2], mockItems[3], mockItems[4], mockItems[5], mockItems[6], mockItems[7]),
                prevKey = null,
                nextKey = null
            ),
            actual = pagingSource.load(
                Refresh(
                    key = null,
                    loadSize = 9,
                    placeholdersEnabled = false
                )
            ) as PagingSource.LoadResult.Page<Int, GitHubUserDTO>
        )
    }
}