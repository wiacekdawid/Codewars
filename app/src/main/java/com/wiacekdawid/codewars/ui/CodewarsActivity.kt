package com.wiacekdawid.codewars.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.wiacekdawid.codewars.R
import com.wiacekdawid.codewars.ui.challengeslist.ChallengesListFragment
import com.wiacekdawid.codewars.ui.memberslist.MembersListFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import android.view.MenuInflater
import com.wiacekdawid.codewars.ui.challengedetails.ChallengeDetailsFragment


/**
 * Created by dawidwiacek on 28/04/2018.
 */

class CodewarsActivity: AppCompatActivity(), AttachedCodewarsActivity, HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_codewars)
        if (savedInstanceState != null) {
            return
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.ac_fl_container, MembersListFragment()).commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override fun openChallenges(memberId: String) {
        val challengesListFragment = ChallengesListFragment()
        var bundle = Bundle()
        bundle.putString(ChallengesListFragment.MEMBER_ID, memberId)
        challengesListFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
                .add(R.id.ac_fl_container, challengesListFragment)
                .addToBackStack(ChallengesListFragment::class.java.name)
                .commit()
    }

    override fun openChallengeDetails(userName: String, challengeId: String, isCompletedChallenge: Boolean) {
        val challengeDetailsFragment = ChallengeDetailsFragment()
        var bundle = Bundle()
        bundle.putString(ChallengeDetailsFragment.USER_NAME, userName)
        bundle.putString(ChallengeDetailsFragment.CHALLENGE_ID, challengeId)
        bundle.putBoolean(ChallengeDetailsFragment.COMPLETED_CHALLENGE, isCompletedChallenge)
        challengeDetailsFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
                .add(R.id.ac_fl_container, challengeDetailsFragment)
                .addToBackStack(ChallengeDetailsFragment::class.java.name)
                .commit()
    }
}