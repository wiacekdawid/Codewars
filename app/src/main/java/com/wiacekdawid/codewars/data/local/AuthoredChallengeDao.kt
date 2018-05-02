package com.wiacekdawid.codewars.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by dawidwiacek on 01/05/2018.
 */

@Dao
interface AuthoredChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authoredChallenge: AuthoredChallenge)

    @Query("DELETE FROM authored_challenge_table")
    fun deleteAll()

    @Query("SELECT * from authored_challenge_table ORDER BY uid ASC")
    fun getAllAuthoredChallenges(): List<AuthoredChallenge>
}