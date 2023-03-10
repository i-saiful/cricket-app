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
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.PlayerAdapter
import com.saiful.cricketapp.databinding.FragmentPlayerBinding
import com.saiful.cricketapp.model.PlayerName
import com.saiful.cricketapp.viewmodels.CricketViewModel

class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel
    private var isSearch = false
    private var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        searchPlayer()
        binding.searchPlayer.editText?.doOnTextChanged { text, _, _, _ ->
            searchPlayer(text.toString())
        }

        // handle key enter
        binding.searchPlayerEdit.setOnKeyListener { KeyView, keyCode, _ ->
            handleKeyEvent(
                KeyView,
                keyCode
            )
        }

        // Bottom navigation hide
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    private fun searchPlayer(query: String = "") {
        val players = mutableListOf<PlayerName>()
        viewModel.players.observe(viewLifecycleOwner) { it ->
            it.map {
                if (it.fullName.contains(query, ignoreCase = true)) {
                    players.add(it)
                }
            }
            // Recycler
            val recycler = binding.playerRecycler
            recycler.setHasFixedSize(true)
            recycler.adapter = PlayerAdapter(players)
        }
        isSearch = players.size == 0
        searchQuery = query
    }

    // Key listener for hiding the keyboard when the "Enter" button is tapped.
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            if (isSearch) {
                Log.d("TAG", "search required: true -> $searchQuery");
                viewModel.fetchPlayers(searchQuery)
            }
            return true
        }
        return false
    }
}