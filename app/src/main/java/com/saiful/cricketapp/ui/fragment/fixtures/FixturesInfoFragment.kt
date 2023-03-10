package com.saiful.cricketapp.ui.fragment.fixtures

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.saiful.cricketapp.R
import com.saiful.cricketapp.databinding.FragmentFixturesInfoBinding
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.FETCH_FROM_INTERNET
import com.saiful.cricketapp.util.Constant.Companion.FIXTURES_ID
import com.saiful.cricketapp.util.Constant.Companion.INTERNET_OFFLINE_MESSAGE
import com.saiful.cricketapp.util.Constant.Companion.LOCAL_TEAM_ID
import com.saiful.cricketapp.util.Constant.Companion.VISITOR_TEAM_ID
import com.saiful.cricketapp.util.Internet.Companion.isOnline
import com.saiful.cricketapp.viewmodels.CricketViewModel
import java.text.SimpleDateFormat
import java.util.*

class FixturesInfoFragment : Fragment() {
    private var _binding: FragmentFixturesInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel
    private lateinit var utilsSharedPreferences: UtilsSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFixturesInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        utilsSharedPreferences = UtilsSharedPreferences(requireContext())
        val fixturesId = utilsSharedPreferences.getData(FIXTURES_ID) ?: ""

        // Swipe Refresh
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            if(isOnline(requireContext())){
                viewModel.fetchFixturesById(fixturesId)
                Snackbar.make(binding.root, FETCH_FROM_INTERNET, Snackbar.LENGTH_LONG).show()
            } else{
                Snackbar.make(binding.root, INTERNET_OFFLINE_MESSAGE, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val fixturesId = utilsSharedPreferences.getData(FIXTURES_ID) ?: ""
        Log.d("TAG", "fixture Id -> : $fixturesId");

        viewModel.getFixtureMatchInfo(fixturesId).observe(viewLifecycleOwner) {
            utilsSharedPreferences.saveData(LOCAL_TEAM_ID, it.localTeamId)
            utilsSharedPreferences.saveData(VISITOR_TEAM_ID, it.visitorTeamId)

            binding.localTeamName.text = getString(R.string.colon_text, it.localTeamName)
            binding.visitorTeamName.text = getString(R.string.colon_text, it.visitorTeamName)
            binding.winnerTeamName.text = getString(R.string.colon_text, it.winnerTeamName ?: "")
            binding.tossWonTeamName.text = getString(R.string.colon_text, it.tossWonTeamName ?: "")
            binding.manOfMatchName.text = getString(R.string.colon_text, it.manOfMatchName ?: "")
            binding.manOfSeriesName.text = getString(R.string.colon_text, it.manOfSeriesName ?: "")
        }

        viewModel.getFixture(fixturesId).observe(viewLifecycleOwner) {
            binding.fixtureTime.text =
                getString(R.string.colon_text, timeConverter(it.starting_at, "time"))
            binding.fixtureDate.text = getString(R.string.colon_text, timeConverter(it.starting_at))
            val venueId = it.venue_id.toString()
            Log.d("TAG", "venueId: $venueId");
            getVenueInfo(venueId)
        }

        viewModel.getFixtureUmpire(fixturesId).observe(viewLifecycleOwner) {
            binding.firstUmpireName.text = getString(R.string.colon_text, it.first_umpire ?: "")
            binding.secondUmpireName.text = getString(R.string.colon_text, it.second_umpire ?: "")
            binding.tvUmpireName.text = getString(R.string.colon_text, it.tv_umpire ?: "")
            binding.refereeName.text = getString(R.string.colon_text, it.referee ?: "")
        }
    }

    private fun getVenueInfo(venueId: String) {
        viewModel.getVenue(venueId).observe(viewLifecycleOwner) {
            binding.venueName.text = getString(R.string.colon_text, it.name)
            binding.venueCityName.text = getString(R.string.colon_text, it.city)
            binding.venueCapacity.text = getString(R.string.colon_text, it.capacity.toString())
        }
    }

    private fun timeConverter(time: String, format: String = "date"): String {
        //parse the UTC timestamp
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val utcDateTime = dateFormat.parse(time)

        //convert to local time
        val localDateTime = Date(requireNotNull(utcDateTime).time)

        //format the local time as desired
        val displayFormat = if (format == "time") "hh:mm a" else "dd MMM, yyyy"
        val timeFormat = SimpleDateFormat(displayFormat, Locale.US)
        return timeFormat.format(localDateTime)
    }

}