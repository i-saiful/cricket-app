package com.saiful.cricketapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saiful.cricketapp.data.Datasource
import com.saiful.cricketapp.model.LiveFixture
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class LiveCricketViewModel : ViewModel() {
    private var _liveFixture = MutableLiveData<List<LiveFixture>>()
    val liveFixture: MutableLiveData<List<LiveFixture>> get() = _liveFixture
    private var _isLive = true
    private var _ballsBowled = 0
    private var _currentOver = 0.0
    private var _wickets = 0
    private val _formatter = DecimalFormat("#0.0")
    private var _score = 0
    private var _liveScore = 0

    init {
        _liveFixture.value = Datasource().loadLiveData()
        startFixture()
    }

    private fun startFixture() {
        viewModelScope.launch {
            while (_isLive) {
                delay(1000)
                simulateFixture()
            }
        }
    }

    private fun simulateFixture() {
        val liveFixture = _liveFixture.value ?: return // return early if value is null
        val updatedFixture = liveFixture.first().copy(
            visitorTeamScore = updateScore(),
            visitorTeamOvers = incrementOver(),
            visitorTeamWickets = updateWickets(),
            note = matchStatus()
        )
        val updatedList = liveFixture.toMutableList().also { it[0] = updatedFixture }
        _liveFixture.postValue(updatedList)
    }

    private fun matchStatus() = "$_currentOver balls $_liveScore runs. ${calculateWinPercentage()}"

    private fun calculateWinPercentage(): String {
        // calculate crr
        val overs = _currentOver.toInt() + 1
        val crr = if (overs != 0) {
            _score.toDouble() / overs
        } else {
            0.0
        }

        // calculate rrr
        val oversLeft = 20 - _currentOver.toInt()
        val rrr = if (oversLeft > 0 && _score > 0) {
            _liveFixture.value!![0].localTeamScore * 100 / _score / oversLeft
        } else {
            0
        }

        val winPercentage = when {
            crr > rrr -> (51..95).random()
            crr < rrr -> (30..49).random()
            else -> 50
        }

        val localTeamScore = _liveFixture.value?.get(0)?.localTeamScore ?: 0
        val visitorTeamCode = _liveFixture.value?.get(0)?.visitorTeamCode
        val localTeamCode = _liveFixture.value?.get(0)?.localTeamCode

        return when {
            _score > localTeamScore -> "$visitorTeamCode won the game"
            _score < localTeamScore -> if (_isLive) "CRR: ${_formatter.format(crr)}, RRR: ${
                _formatter.format(
                    rrr
                )
            } WIN(${visitorTeamCode}) $winPercentage%"
            else "$localTeamCode won the game"
            else -> "Match Drawn"
        }
    }

    private fun updateScore(): Int {
        _liveScore = (0..6).random()
        _score += _liveScore
        if (_score > (_liveFixture.value?.get(0)?.localTeamScore ?: 0)) {
            _isLive = false
        }
        return _score
    }

    private fun updateWickets(): Int {
        if ((1..100).random() == 71) {
            _wickets++
        } else {
            _wickets
        }
        if (_wickets == 10) {
            _isLive = false
        }
        return _wickets
    }

    private fun incrementOver(): Double {
        _ballsBowled++

        when (_ballsBowled) {
            in 1..5 -> {
                _currentOver += 0.1f
            }
            6 -> {
                _currentOver += 0.5f
                _ballsBowled = 0
            }
        }
        if (_currentOver >= 20.0) {
            _isLive = false
        }
        val formattedOver = _formatter.format(_currentOver)
        _currentOver = formattedOver.toDouble()
        return _currentOver
    }
}