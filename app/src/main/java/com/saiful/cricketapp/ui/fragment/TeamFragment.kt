package com.saiful.cricketapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.TeamAdapter
import com.saiful.cricketapp.database.entity.Teams
import com.saiful.cricketapp.databinding.FragmentTeamBinding
import com.saiful.cricketapp.util.Constant.Companion.FETCH_FROM_INTERNET
import com.saiful.cricketapp.util.Constant.Companion.INTERNET_OFFLINE_MESSAGE
import com.saiful.cricketapp.util.Internet.Companion.isOnline
import com.saiful.cricketapp.viewmodels.CricketViewModel

class TeamFragment : Fragment() {
    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        // Swipe Refresh
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            if (isOnline(requireContext())) {
                viewModel.fetchTeams()
                Snackbar.make(binding.root, FETCH_FROM_INTERNET, Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(binding.root, INTERNET_OFFLINE_MESSAGE, Snackbar.LENGTH_LONG).show()
            }
        }

        // Search handle
        searchTeam()
        binding.searchTeam.editText?.doOnTextChanged { text, _, _, _ ->
            searchTeam(text.toString())
        }
        // handle key enter
        binding.searchTeamEdit.setOnKeyListener { KeyView, keyCode, _ ->
            handleKeyEvent(
                KeyView,
                keyCode
            )
        }

        viewModel.teamsCount.observe(viewLifecycleOwner) {
            Log.d("TAG", "Teams table has ${it.count} rows of data");
            if (it.count == 0) {
                viewModel.fetchTeams()
            }
        }

        // Bottom navigation hide
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    private fun searchTeam(query: String = "") {
        val teams = mutableListOf<Teams>()
        viewModel.teams.observe(viewLifecycleOwner) { it ->
            it.map {
                if (it.name.contains(query, ignoreCase = true)) {
                    teams.add(it)
                }
            }
            // Recycler
            val recycler = binding.teamRecycler
            recycler.setHasFixedSize(true)
            recycler.adapter = TeamAdapter(teams)
        }
    }

    // Key listener for hiding the keyboard when the "Enter" button is tapped.
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}