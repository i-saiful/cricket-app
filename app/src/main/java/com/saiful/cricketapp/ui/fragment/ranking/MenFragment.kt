package com.saiful.cricketapp.ui.fragment.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.RankingAdapter
import com.saiful.cricketapp.databinding.FragmentMenBinding
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.MEN_GENDER
import com.saiful.cricketapp.util.Constant.Companion.ODI
import com.saiful.cricketapp.util.Constant.Companion.RANKING_TYPE
import com.saiful.cricketapp.util.Constant.Companion.T20I
import com.saiful.cricketapp.util.Constant.Companion.TEST
import com.saiful.cricketapp.viewmodels.CricketViewModel

class MenFragment : Fragment() {
    private var _binding: FragmentMenBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel
    private lateinit var utilsSharedPreferences: UtilsSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenBinding.inflate(inflater, container, false)
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

        rankingList()
        binding.rankingTest.setOnClickListener {
            utilsSharedPreferences.saveData(RANKING_TYPE, TEST)
            rankingList()
        }
        binding.rankingOdi.setOnClickListener {
            utilsSharedPreferences.saveData(RANKING_TYPE, ODI)
            rankingList()
        }
        binding.rankingT20i.setOnClickListener {
            utilsSharedPreferences.saveData(RANKING_TYPE, T20I)
            rankingList()
        }
    }

    private fun rankingList() {
        val rankingType = utilsSharedPreferences.getData(RANKING_TYPE) ?: ""
        val recycler = binding.rankingRecycler
        recycler.setHasFixedSize(true)
        viewModel.ranking(rankingType, MEN_GENDER).observe(viewLifecycleOwner) {
            // Recycler
            recycler.adapter = RankingAdapter(it)
        }
        changeBackgroundColor(rankingType)
    }

    // change background
    private fun changeBackgroundColor(team: String) {
        val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
        val colorPlum = ContextCompat.getColor(requireContext(), R.color.plum)
        val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
        when (team) {
            TEST -> {
                binding.rankingTest.setBackgroundColor(colorPlum)
                binding.rankingTest.setTextColor(colorWhite)

                binding.rankingOdi.setBackgroundColor(colorWhite)
                binding.rankingOdi.setTextColor(colorBlack)
                binding.rankingOdi.setStrokeColorResource(R.color.plum)

                binding.rankingT20i.setBackgroundColor(colorWhite)
                binding.rankingT20i.setTextColor(colorBlack)
                binding.rankingT20i.setStrokeColorResource(R.color.plum)
            }
            ODI -> {
                binding.rankingOdi.setBackgroundColor(colorPlum)
                binding.rankingOdi.setTextColor(colorWhite)

                binding.rankingTest.setBackgroundColor(colorWhite)
                binding.rankingTest.setTextColor(colorBlack)
                binding.rankingTest.setStrokeColorResource(R.color.plum)

                binding.rankingT20i.setBackgroundColor(colorWhite)
                binding.rankingT20i.setTextColor(colorBlack)
                binding.rankingT20i.setStrokeColorResource(R.color.plum)
            }
            else -> {
                binding.rankingT20i.setBackgroundColor(colorPlum)
                binding.rankingT20i.setTextColor(colorWhite)

                binding.rankingTest.setBackgroundColor(colorWhite)
                binding.rankingTest.setTextColor(colorBlack)
                binding.rankingTest.setStrokeColorResource(R.color.plum)

                binding.rankingOdi.setBackgroundColor(colorWhite)
                binding.rankingOdi.setTextColor(colorBlack)
                binding.rankingOdi.setStrokeColorResource(R.color.plum)
            }
        }
    }
}