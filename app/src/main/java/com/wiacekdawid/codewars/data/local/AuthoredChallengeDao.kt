package com.wiacekdawid.codewars.data.local

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.wiacekdawid.codewars.data.local.model.AuthoredChallenge
import io.reactivex.Maybe

/**
 * Created by dawidwiacek on 01/05/2018.
 */

@Dao
interface AuthoredChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authoredChallenge: AuthoredChallenge)

    @Query("DELETE FROM authored_challenge_table")
    fun deleteAll()

    @Query("SELECT * from authored_challenge_table WHERE userName=:userName ORDER BY id ASC")
    fun getAllAuthoredChallengesForUserName(userName: String): DataSource.Factory<Int, AuthoredChallenge>

    @Query("SELECT * from authored_challenge_table WHERE id=:id LIMIT 1")
    fun getAuthoredChallengeForId(id: String): Maybe<AuthoredChallenge>
}