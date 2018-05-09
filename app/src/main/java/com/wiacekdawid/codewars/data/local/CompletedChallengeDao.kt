package com.wiacekdawid.codewars.data.local

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

/**
 * Created by dawidwiacek on 01/05/2018.
 */

@Dao
interface CompletedChallengeDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    fun insert(completedChallage: CompletedChallenge)

    @Query("DELETE FROM completed_challenge_table")
    fun deleteAll()

    @Query("SELECT * from completed_challenge_table WHERE userName=:userName ORDER BY id ASC")
    fun getAllCompletedChallengesForMember(userName: String): DataSource.Factory<Int, CompletedChallenge>

    @Query("SELECT * from completed_challenge_table WHERE id=:id")
    fun getCompletedChallengesForId(id: String): Single<CompletedChallenge>
}