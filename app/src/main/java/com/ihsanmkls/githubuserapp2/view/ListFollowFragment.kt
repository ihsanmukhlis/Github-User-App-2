package com.ihsanmkls.githubuserapp2.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsanmkls.githubuserapp2.adapter.UsersAdapter
import com.ihsanmkls.githubuserapp2.databinding.FragmentListFollowBinding
import com.ihsanmkls.githubuserapp2.viewmodel.ListFollowViewModel

class ListFollowFragment : Fragment() {

    private lateinit var adapter: UsersAdapter
    private var _binding: FragmentListFollowBinding? = null
    private val binding get() = _binding!!
    private val listFollowViewModel by viewModels<ListFollowViewModel>()

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int) =
            ListFollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        showLoading(true)
        showRecyclerView()

        val user = activity?.intent?.getStringExtra(DetailUserActivity.EXTRA_USER)
        when (index) {
            1 -> listFollowViewModel.followersDetail(user.toString())
            else -> listFollowViewModel.followingDetail(user.toString())
        }

        listFollowViewModel.listFollow.observe(viewLifecycleOwner) { listUser ->
            showLoading(false)
            adapter.setDataUser(listUser)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRecyclerView() {
        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        val rv = binding.rvListFollow
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        rv.setHasFixedSize(true)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}