package com.saiful.cricketapp.adapter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saiful.cricketapp.R
import com.saiful.cricketapp.model.FixturesMatch
import com.saiful.cricketapp.receiver.NotificationReceiver
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.FIXTURES_ID
import com.saiful.cricketapp.util.Constant.Companion.MATCH_STATUS_ABANDONED
import com.saiful.cricketapp.util.Constant.Companion.MATCH_STATUS_UPCOMING
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class FixturesMatchAdapter(private val fixturesMatch: List<FixturesMatch>) :
    RecyclerView.Adapter<FixturesMatchAdapter.FixturesMatchViewHolder>() {
    class FixturesMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stageName: TextView = view.findViewById(R.id.stage_name)
        val roundName: TextView = view.findViewById(R.id.round_name)
        val matchStatus: TextView = view.findViewById(R.id.match_status)
        val localTeamCode: TextView = view.findViewById(R.id.local_team_code)
        val localTeamRunWicket: TextView = view.findViewById(R.id.local_team_run_wicket)
        val localTeamOver: TextView = view.findViewById(R.id.local_team_over)
        val visitorTeamOver: TextView = view.findViewById(R.id.visitor_team_over)
        val visitorTeamRunWicket: TextView = view.findViewById(R.id.visitor_team_run_wicket)
        val visitorTeamCode: TextView = view.findViewById(R.id.visitor_team_code)
        val matchNote: TextView = view.findViewById(R.id.match_note)

        val layoutLocalTeam: LinearLayout =
            view.findViewById(R.id.layout_local_team_run_wicket_over)
        val layoutVisitorTeam: LinearLayout =
            view.findViewById(R.id.layout_visitor_team_run_wicket_over)

        // add a timer variable to keep track of the running countdown timers
        var timer: CountDownTimer? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixturesMatchViewHolder {
        return FixturesMatchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fixtures_match, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return fixturesMatch.size
    }

    override fun onBindViewHolder(holder: FixturesMatchViewHolder, position: Int) {
        val match = fixturesMatch[position]
        val context = holder.itemView.context

        holder.stageName.text = match.stage
        holder.roundName.text = context.getString(
            R.string.round_date,
            match.round,
            timeConverter(match.starting_at, "time")
        )

        val matchStatusUpcoming = "Live"
        val matchStatusAbandoned = context.getString(R.string.match_status_abandoned)
        val matchStatusFinished = context.getString(R.string.match_status_finished)

        when (match.status) {
            MATCH_STATUS_UPCOMING -> {
                // cancel any previous countdown timers before starting a new one
                holder.timer?.cancel()
                holder.matchStatus.textSize = 14f

                val date = match.starting_at
                val dateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
                val futureTime = dateFormat.parse(date)
                val currentTime = Date()
                var duration = futureTime?.time?.minus(currentTime.time) ?: 0

                if (duration > 0) {
                    val timer = object : CountDownTimer(duration, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished) / 2
                            val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                            val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                            val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                            // update the duration variable for the next tick
                            duration = millisUntilFinished

                            // format the countdown text based on the remaining time
                            val countdownText = if (days >= 1) {
                                // if one or more days remaining, show the remaining time in days
                                String.format(
                                    "%d day%s %02d:%02d:%02d",
                                    days,
                                    if (days == 1L) "" else "s",
                                    hours,
                                    minutes,
                                    seconds
                                )
                            } else {
                                // otherwise, show the remaining time in hours, minutes, and seconds
                                String.format("%02d:%02d:%02d", hours, minutes, seconds)
                            }

                            holder.matchStatus.text = countdownText
                        }

                        override fun onFinish() {
                            holder.matchStatus.text = matchStatusUpcoming
                        }

                    }
                    holder.timer = timer
                    timer.start()
                } else {
                    holder.timer?.cancel()
                    holder.matchStatus.text = matchStatusUpcoming
                }
                holder.matchStatus.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
                holder.matchStatus.setTextColor(ContextCompat.getColor(context, R.color.black))
                notificationSet(match, context)
            }
            MATCH_STATUS_ABANDONED -> {
                holder.timer?.cancel()
                holder.matchStatus.text = matchStatusAbandoned
                holder.matchStatus.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.matchStatus.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.gray)
            }
            else -> {
                holder.timer?.cancel()
                holder.matchStatus.text = matchStatusFinished
                holder.matchStatus.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.matchStatus.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.forest_green)
            }
        }

        if (match.status == MATCH_STATUS_UPCOMING) {
            holder.stageName.text = match.stage
            holder.layoutLocalTeam.visibility = View.GONE
            holder.layoutVisitorTeam.visibility = View.GONE

            holder.localTeamCode.text = match.localteam_name
            holder.visitorTeamCode.text = match.visitorteam_name
            holder.matchNote.text = context.getString(
                R.string.match_venue_name_city,
                match.venue_name,
                match.venue_city
            )
            holder.matchNote.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        } else {
            match.localteam_overs?.let {
                holder.layoutLocalTeam.visibility = View.VISIBLE
                holder.localTeamRunWicket.text = context.getString(
                    R.string.run_wicket,
                    match.localteam_score.toString(),
                    match.localteam_wickets.toString()
                )
                holder.localTeamOver.text =
                    context.getString(R.string.match_over, match.localteam_overs.toString())
            } ?: run {
                holder.layoutLocalTeam.visibility = View.GONE
            }

            match.visitorteam_overs?.let {
                holder.layoutVisitorTeam.visibility = View.VISIBLE
                holder.visitorTeamRunWicket.text = context.getString(
                    R.string.run_wicket,
                    match.visitorteam_score.toString(),
                    match.visitorteam_wickets.toString()
                )
                holder.visitorTeamOver.text =
                    context.getString(R.string.match_over, match.visitorteam_overs.toString())
            } ?: run {
                holder.layoutVisitorTeam.visibility = View.GONE
            }

            holder.stageName.text = timeConverter(match.starting_at, "date")
            holder.localTeamCode.text = match.localteam_code
            holder.visitorTeamCode.text = match.visitorteam_code
            holder.matchNote.text = match.note
            holder.matchNote.setTextColor(ContextCompat.getColor(context, R.color.deep_pink))
            holder.matchNote.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }


        // local team image set
        Glide
            .with(holder.itemView.context)
            .load(match.localteam_image)
            .fitCenter()
            .thumbnail(
                Glide.with(holder.itemView.context)
                    .load(R.drawable.flag_loading)
            )
            .into(holder.itemView.findViewById(R.id.local_team_image))

        // visitor team image set
        Glide
            .with(holder.itemView.context)
            .load(match.visitorteam_image)
            .fitCenter()
            .thumbnail(
                Glide.with(holder.itemView.context)
                    .load(R.drawable.flag_loading)
            )
            .into(holder.itemView.findViewById(R.id.visitor_team_image))

        // save fixtures id in shared preferences
        holder.itemView.setOnClickListener {
            val utilsSharedPreferences = UtilsSharedPreferences(it.context)
            utilsSharedPreferences.saveData(FIXTURES_ID, match.fixtures_id)
            val title = "${match.localteam_code} vs ${match.visitorteam_code}"
            val bundle = bundleOf("title" to title)
            it.findNavController().navigate(
                R.id.fixturesFragment,
                bundle,
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build()
            )
        }
    }

    private fun notificationSet(match: FixturesMatch, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val notificationIntent = Intent(context, NotificationReceiver::class.java)
        notificationIntent.putExtra("message", "The match is starting soon!")
        notificationIntent.putExtra("title", "${match.localteam_code} vs ${match.visitorteam_code}")

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // Schedule the notification 15 minutes before the match starts
        val startTime = match.starting_at
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
        val startDate = dateFormat.parse(startTime)
        val notificationTime = startDate?.time?.minus(15 * 60 * 1000) ?: 0

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            notificationTime,
            pendingIntent
        )
    }

    private fun timeConverter(time: String, format: String): String {
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