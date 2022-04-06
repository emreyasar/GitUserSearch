package com.yasaremre.gitusersearch.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yasaremre.gitusersearch.R
import com.yasaremre.gitusersearch.core.Dispatchers
import com.yasaremre.gitusersearch.databinding.FragmentSearchBinding
import com.yasaremre.gitusersearch.ui.search.adapter.UserComparator
import com.yasaremre.gitusersearch.ui.search.adapter.UserListRecyclerViewAdapter
import com.yasaremre.gitusersearch.ui.search.listener.ViewHolderClickListener
import com.yasaremre.gitusersearch.ui.search.paging.SearchListLoadStateAdapter
import com.yasaremre.gitusersearch.ui.search.viewholder.UserListViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    /**
     * Holds binding object to access UI components.
     */
    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var adapter: UserListRecyclerViewAdapter

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProperties()
        setUpViews()
    }

    override fun onResume() {
        super.onResume()
        searchUsers()
    }

    private fun initProperties() {
        adapter = UserListRecyclerViewAdapter(object : ViewHolderClickListener<RecyclerView.ViewHolder>() {
            override fun onClick(holder: RecyclerView.ViewHolder) {
                when (holder) {
                    is UserListViewHolder -> {
                        val user = holder.binding.user
                        user?.login?.let {
                            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(it))
                        }
                    }
                }
            }
        }, UserComparator)

        adapter.addLoadStateListener { loadState ->
            val isEmptyList = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            binding.noResultFoundTextView.isVisible = isEmptyList
            // Only show the list if refresh succeeds
            binding.userListRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails
            binding.errorTextView.isVisible = loadState.source.refresh is LoadState.Error
            if (binding.errorTextView.isVisible) {
                binding.errorTextView.text =
                    (loadState.source.refresh as LoadState.Error).error.toString()
            }
        }

    }

    private fun setUpViews() {
        binding.userListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.userListRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = SearchListLoadStateAdapter(adapter::retry),
            footer = SearchListLoadStateAdapter(adapter::retry)
        )
    }

    private fun searchUsers() {
        if (!viewModel.searchText.value.isNullOrEmpty()) {
            // Make sure we cancel the previous job before creating a new one
            searchJob?.cancel()
            searchJob = lifecycleScope.launch(Dispatchers.io()) {
                viewModel.searchUsers(viewModel.searchText.value!!).distinctUntilChanged()
                    .collectLatest {
                        adapter.submitData(it)
                    }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                searchUsers()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchText.value = newText
                return false
            }
        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

}