package com.saiful.cricketapp.ui.fragment.playerStatistic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.saiful.cricketapp.R
import com.saiful.cricketapp.databinding.FragmentPlayerProfileBinding
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.FETCH_FROM_INTERNET
import com.saiful.cricketapp.util.Constant.Companion.INTERNET_OFFLINE_MESSAGE
import com.saiful.cricketapp.util.Constant.Companion.PLAYER_ID
import com.saiful.cricketapp.util.Internet.Companion.isOnline
import com.saiful.cricketapp.viewmodels.CricketViewModel
import java.text.SimpleDateFormat
import java.util.*

class PlayerProfileFragment : Fragment() {
    private var _binding: FragmentPlayerProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        val utilsSharedPreferences = UtilsSharedPreferences(requireContext())
        val playerId = utilsSharedPreferences.getData(PLAYER_ID) ?: ""
        Log.d("TAG", "playerId: $playerId");

        viewModel.playerInfo(playerId).observe(viewLifecycleOwner) {
//            Log.d("TAG", "value: $it")
            binding.playerFullName.text = getString(R.string.colon_text, it.fullName)
            binding.playerDateOfBirth.text = getString(R.string.colon_text,
                it?.date_of_birth?.let { it1 -> dateOfBirth(it1) })
            binding.playerGender.text =
                getString(R.string.colon_text, if (it.gender == "m") "Male" else "Female")
            binding.playerBattingStyle.text = getString(R.string.colon_text,
                it.batting_style?.let { batting ->
                    batting.split("-")
                        .joinToString(separator = " ") { itJoin ->
                            itJoin.replaceFirstChar { itChar ->
                                itChar.uppercase()
                            }
                        }
                } ?: ""
            )
            binding.playerBowlingStyle.text = getString(R.string.colon_text,
                it.bowling_style?.let { bowling ->
                    bowling.split("-")
                        .joinToString(separator = " ") { itJoin ->
                            itJoin.replaceFirstChar { itChar ->
                                itChar.uppercase()
                            }
                        }
                } ?: ""
            )
            binding.playerPositionName.text = getString(R.string.colon_text, it.position_name)
            binding.playerCountryName.text = getString(R.string.colon_text, it.countryName)
        }

        // check -> have player batting career
        viewModel.getPlayerCareer(playerId).observe(viewLifecycleOwner) {
            Log.d("TAG", "Found! Player career in player career table -> ${it.count}")
            if (it.count == 0) {
                viewModel.fetchPlayerCareer(playerId)
            }
        }

        // Swipe Refresh
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            if (isOnline(requireContext())) {
                viewModel.fetchPlayerCareer(playerId)
                Snackbar.make(binding.root, FETCH_FROM_INTERNET, Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(binding.root, INTERNET_OFFLINE_MESSAGE, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun dateOfBirth(birth: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        val date = inputFormat.parse(birth)
        val dateOfBirth = outputFormat.format(date!!)

        // age calculate
        val calendar = Calendar.getInstance()
        calendar.time = date
        val currentDate = Calendar.getInstance()
        var age = currentDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
        if (currentDate.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return "$dateOfBirth ($age years)"
    }
}