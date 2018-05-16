package com.wiacekdawid.codewars.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.wiacekdawid.codewars.data.local.model.AuthoredChallenge
import com.wiacekdawid.codewars.data.local.model.CompletedChallenge
import com.wiacekdawid.codewars.data.local.model.Member

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Database(entities = [Member::class, CompletedChallenge::class, AuthoredChallenge::class], version = 1)
abstract class CodewarsDatabase : RoomDatabase() {
    abstract fun membersDao(): MemberDao
    abstract fun completedChallengeDao(): CompletedChallengeDao
    abstract fun authoredChallengeDao(): AuthoredChallengeDao
}