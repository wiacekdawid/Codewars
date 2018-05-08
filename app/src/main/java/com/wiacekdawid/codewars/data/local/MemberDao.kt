package com.wiacekdawid.codewars.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe
import io.reactivex.Observable

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(member: Member)

    @Query("DELETE FROM member_table")
    fun deleteAll()

    @Query("SELECT * from member_table ORDER BY userName ASC")
    fun getAllMembers(): List<Member>

    @Query("SELECT * from member_table ORDER BY lastSearchTime ASC")
    fun getAllMembersSortedByLastSearchedTime(): Maybe<List<Member>>

    @Query("SELECT * from member_table ORDER BY rank ASC")
    fun getAllMembersSortedByRank(): Maybe<List<Member>>

    @Query("SELECT * from member_table WHERE userName=:userName")
    fun getMember(userName: String): Maybe<Member>
}