package com.dhilasadrah.kadesubmission4

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dhilasadrah.kadesubmission4.R.id.*
import com.dhilasadrah.kadesubmission4.activity.MainActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchSearchTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun tesAppBehaviour(){
        //Aplikasi terbuka dan memastikan daftar liga tampil
        Thread.sleep(2000)
        onView(withId(rvLeagueList)).check(matches(isDisplayed()))

        //Memberi tindakan klik pada item pertama (index ke-0) di recyclerview league list
        onView(withId(rvLeagueList)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(rvLeagueList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //Memastikan fragment detail liga dan BottomNavigationView telah ditampilkan
        Thread.sleep(3000)
        onView(withId(navigation_detail)).check(matches(isDisplayed()))
        onView(withId(nav_view)).check(matches(isDisplayed()))

        //Memberikan tindakan klik pada menu navigasi "match" di BottomNavigationView.
        onView(withId(navigation_match)).perform(click())

        //Memastikan last match list dan menu search ditampilkan
        Thread.sleep(2000)
        onView(withId(rvLastMatch)).check(matches(isDisplayed()))
        onView(withId(search_menu)).check(matches(isDisplayed()))

        //Memberi tindakan klik pada menu search
        onView(withId(search_menu)).perform(click())

        //Memastikan searchview telah ditampilkan
        onView(withId(searchView)).check(matches(isDisplayed()))

        //Menuliskan text 'arsenal' pada searchView dan menekan tombol enter pada keyboard
        onView(withId(searchView)).perform(typeSearchViewText("arsenal"), pressKey(KeyEvent.KEYCODE_ENTER))

        //Memastikan hasil pencarian match muncul
        Thread.sleep(2000)
        onView(withId(rv_matchSearch)).check(matches(isDisplayed()))

        //Memberikan aksi klik pada item kelime (index ke-4) di hasil pencarian match dan memastikan hasil detail match muncul
        onView(withId(rv_matchSearch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
        onView(withId(rv_matchSearch)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))

        //Memastikan halaman detail match muncul
        Thread.sleep(3000)
        onView(withId(match_detail)).check(matches(isDisplayed()))

        //Menekan tombol kembali
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())

        //Memastikan recyclerview match search dan menu searchview tampil
        onView(withId(rv_matchSearch)).check(matches(isDisplayed()))
        onView(withId(searchView)).check(matches(isDisplayed()))

        //Memberikan aksi klik pada searchView
        Thread.sleep(2000)
        onView(withId(searchView)).perform(click())

        //Menuliskan text random 'zxc' pada searchView dan menekan tombol enter pada keyboard
        onView(withId(searchView)).perform(typeSearchViewText("zxc"), pressKey(KeyEvent.KEYCODE_ENTER))

        //Memastikan gambar dan text 'No Data Found' ditampilkan saat hasil pencarian tidak ditemukan
        Thread.sleep(3000)
        onView(withId(imgEmpty)).check(matches(isDisplayed()))
        onView(withId(tvEmpty)).check(matches(isDisplayed()))

        //Memberikan aksi klik pada searchView
        onView(withId(searchView)).perform(click())

        //Menuliskan text 'man united' pada searchView dan menekan tombol enter pada keyboard
        onView(withId(searchView)).perform(typeSearchViewText("man united"), pressKey(KeyEvent.KEYCODE_ENTER))

        //Memastikan hasil pencarian pertandingan muncul
        Thread.sleep(3000)
        onView(withId(rv_matchSearch)).check(matches(isDisplayed()))

        //aplikasi tertutup
    }
}

fun typeSearchViewText(text: String?): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(
                isDisplayed(),
                isAssignableFrom(SearchView::class.java)
            )
        }

        override fun getDescription(): String {
            return "Change view text"
        }

        override fun perform(
            uiController: UiController,
            view: View
        ) {
            (view as SearchView).setQuery(text, false)
        }
    }
}

