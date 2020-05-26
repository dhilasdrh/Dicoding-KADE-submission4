package com.dhilasadrah.kadesubmission4.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dhilasadrah.kadesubmission4.R
import com.dhilasadrah.kadesubmission4.api.ApiRepository
import com.dhilasadrah.kadesubmission4.model.Favorite
import com.dhilasadrah.kadesubmission4.database.database
import com.dhilasadrah.kadesubmission4.model.MatchDetail
import com.dhilasadrah.kadesubmission4.model.Team
import com.dhilasadrah.kadesubmission4.presenter.MatchDetailPresenter
import com.dhilasadrah.kadesubmission4.util.*
import com.dhilasadrah.kadesubmission4.view.MatchDetailView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {
    private lateinit var match: MatchDetail
    private lateinit var matchId: String
    private lateinit var presenter: MatchDetailPresenter
    private var isFavorite: Boolean = false
    private var isLastMatch: Boolean? = false
    private var menuItem: Menu? = null

    companion object {
        const val ID_HOME_TEAM = "id_home_team"
        const val ID_AWAY_TEAM = "id_away_team"
        const val ID_MATCH = "id_match"
        const val MATCH_STATUS = "match_status"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        presenter = MatchDetailPresenter(this, ApiRepository(), Gson())

        val idHomeTeam = intent?.getStringExtra(ID_HOME_TEAM)
        val idAwayTeam = intent?.getStringExtra(ID_AWAY_TEAM)
        isLastMatch = intent?.getBooleanExtra(MATCH_STATUS, false)
        matchId = intent?.getStringExtra(ID_MATCH).toString()

        presenter.getMatchDetail(matchId)
        presenter.getTeamBadge(idHomeTeam, true)
        presenter.getTeamBadge(idAwayTeam, false)

        favoriteState()
        supportActionBar?.title = getString(R.string.matchdetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(MATCH_ID = {id})", "id" to matchId)
            val favorite = result.parseList(classParser<Favorite>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav_fill)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav_outline)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_fav -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.MATCH_ID to match.eventId,
                    Favorite.MATCH_DATE to match.eventDate,
                    Favorite.MATCH_TIME to match.eventTime,
                    Favorite.HOME_TEAM_ID to match.homeTeamId,
                    Favorite.HOME_TEAM_NAME to match.homeTeam,
                    Favorite.HOME_TEAM_SCORE to match.homeScore,
                    Favorite.AWAY_TEAM_ID to match.awayTeamId,
                    Favorite.AWAY_TEAM_NAME to match.awayTeam,
                    Favorite.AWAY_TEAM_SCORE to match.awayScore,
                    Favorite.STATUS to isLastMatch
                )
            }
            Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(MATCH_ID = {id})",
                    "id" to "${match.eventId}"
                )
            }
            Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        pbMatchDetail.visible()
    }

    override fun hideLoading() {
        pbMatchDetail.invisible()
    }

    override fun showMatchDetail(data: MatchDetail) {
        match = data

        detail_matchDate.text = data.eventDate?.simpleDate()
        detail_matchTime.text = data.eventTime?.simpleTime()

        detail_homeTeam.text = data.homeTeam
        detail_homeScore.text = data.homeScore
        home_goals.text = data.homeGoalDetails?.parseDetail()
        home_redCards.text = data.homeRedCards?.parseDetail()
        home_yellowCards.text = data.homeYellowCards?.parseDetail()
        home_goalKeeper.text = data.homeGoalKeeper?.parseLineup()
        home_defenders.text = data.homeDefense?.parseLineup()
        home_midfielders.text = data.homeMidfield?.parseLineup()
        home_forwards.text = data.homeForward?.parseLineup()
        home_substitutes.text = data.homeSubstitutes?.parseLineup()

        detail_awayTeam.text = data.awayTeam
        detail_awayScore.text = data.awayScore
        away_goals.text = data.awayGoalDetails?.parseDetail()
        away_redCards.text = data.awayRedCards?.parseDetail()
        away_yellowCards.text = data.awayYellowCards?.parseDetail()
        away_goalKeeper.text = data.awayGoalKeeper?.parseLineup()
        away_defenders.text = data.awayDefense?.parseLineup()
        away_midfielders.text = data.awayMidfield?.parseLineup()
        away_forwards.text = data.awayForward?.parseLineup()
        away_substitutes.text = data.awaySubstitutes?.parseLineup()
    }

    override fun showTeamBadge(team: Team, isHomeTeam: Boolean) {
        Picasso.get().load(team.teamBadge).fit().into(
            if (isHomeTeam) {
                detail_homeBadge
            } else detail_awayBadge
        )
    }
}