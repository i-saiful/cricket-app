package com.saiful.cricketapp.ui.fragment

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.FixturesMatchAdapter
import com.saiful.cricketapp.databinding.FragmentHomeBinding
import com.saiful.cricketapp.receiver.NotificationReceiver
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.NOTIFY_USER
import com.saiful.cricketapp.viewmodels.CricketViewModel
import com.saiful.cricketapp.viewmodels.LiveCricketViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        // check Fixtures Count
        viewModel.fixturesCount.observe(viewLifecycleOwner) {
            Log.d("TAG", "Fixtures table has ${it.count} rows of data")
            if (it.count == 0) {
                viewModel.fetchFixtures()
            }
        }

        viewModel.teamsCount.observe(viewLifecycleOwner) {
            Log.d("TAG", "Teams table has ${it.count} rows of data");
            if (it.count == 0) {
                viewModel.fetchTeams()
            }
        }

        viewModel.countriesSize.observe(viewLifecycleOwner) {
            Log.d("TAG", "Countries table has ${it.count} rows of data");
            if (it.count == 0) {
                viewModel.fetchCountries()
            }
        }

        viewModel.leaguesSize.observe(viewLifecycleOwner) {
            Log.d("TAG", "Leagues table has ${it.count} rows of data");
            if (it.count == 0) {
                viewModel.fetchLeagues()
            }
        }

        viewModel.stagesCount.observe(viewLifecycleOwner) {
            Log.d("TAG", "Stages table has ${it.count} rows of data");
            if (it.count == 0) {
                viewModel.fetchStages()
            }
        }

        viewModel.venuesSize.observe(viewLifecycleOwner) {
            Log.d("TAG", "Venues table has ${it.count} rows of data");
            if (it.count == 0) {
                viewModel.fetchVenues()
            }
        }

        viewModel.seasonsCount.observe(viewLifecycleOwner) {
            Log.d("TAG", "Seasons table has ${it.count} rows of data");
            if (it.count == 0) {
                viewModel.fetchSeasons()
            }
        }

        viewModel.officialsCount.observe(viewLifecycleOwner) {
            Log.d("TAG", "Officials(Umpire) table has ${it.count} rows of data");
            if (it.count == 0) {
                viewModel.fetchOfficials()
            }
        }

        // recent matches
        val recycler = binding.recentMatchRecycler
        recycler.setHasFixedSize(true)
        viewModel.getRecentFixturesMatches.observe(viewLifecycleOwner) {
            Log.d("TAG", "Found! Recent ${it.size} matches")
            recycler.adapter = FixturesMatchAdapter(it)
        }

        // upcoming fixture
        viewModel.getUpcomingFixture.observe(viewLifecycleOwner) {
            Log.d("TAG", "Found! Upcoming ${it.count} matches")
            val utilsSharedPreferences = UtilsSharedPreferences(requireContext())
            if (utilsSharedPreferences.getData(NOTIFY_USER).toBoolean()) {
                Log.d("TAG", "Notification: ON")
                notificationSet(it.count)
                utilsSharedPreferences.saveData(NOTIFY_USER, "false")
            } else {
                Log.d("TAG", "Notification: OFF")
            }
        }

        // Bottom navigation show
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE

        val liveViewModel: LiveCricketViewModel by viewModels()
        liveViewModel.liveFixture.observe(viewLifecycleOwner) {
            Log.d("LIVE", "match: ${it.size}")
            Log.d("LIVE", "over: ${it[0].visitorTeamOvers}")
            Log.d("LIVE", "run: ${it[0].visitorTeamScore}")
            Log.d("LIVE", "wicket: ${it[0].visitorTeamWickets}")
            Log.d("LIVE", "commentary: ${it[0].note}")
            Log.d("LIVE", "---------------------------")

            // local team
            binding.liveLocalTeamCode.text = it[0].localTeamCode
            binding.liveLocalTeamOver.text =
                getString(R.string.match_over, it[0].localTeamOvers.toString())
            binding.liveLocalTeamRunWicket.text = getString(
                R.string.run_wicket,
                it[0].localTeamScore.toString(),
                it[0].localTeamWickets.toString()
            )

            // visitor team
            binding.liveVisitorTeamCode.text = it[0].visitorTeamCode
            binding.liveVisitorTeamOver.text =
                getString(R.string.match_over, it[0].visitorTeamOvers.toString())
            binding.liveVisitorTeamRunWicket.text = getString(
                R.string.run_wicket,
                it[0].visitorTeamScore.toString(),
                it[0].visitorTeamWickets.toString()
            )

            binding.liveStageName.text = it[0].stage
            binding.liveRoundName.text = it[0].round
            binding.liveMatchNote.text = it[0].note

            Glide
                .with(requireContext())
                .load(it[0].localTeamImage)
                .fitCenter()
                .thumbnail(
                    Glide.with(requireContext())
                        .load(R.drawable.flag_loading)
                )
                .into(binding.liveLocalTeamImage)

            Glide
                .with(requireContext())
                .load(it[0].visitorTeamImage)
                .fitCenter()
                .thumbnail(
                    Glide.with(requireContext())
                        .load(R.drawable.flag_loading)
                )
                .into(binding.liveVisitorTeamImage)
        }
    }

    private fun notificationSet(count: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create the notification intent
        val notificationIntent = Intent(requireContext(), NotificationReceiver::class.java)
        notificationIntent.putExtra("message", "You have $count upcoming fixtures.")
        notificationIntent.putExtra("title", "Upcoming Fixtures ($count)")
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // Schedule the notification
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationTime = System.currentTimeMillis() + 10000 // 1 minutes from now
        alarmManager.set(AlarmManager.RTC_WAKEUP, notificationTime, pendingIntent)
    }
}