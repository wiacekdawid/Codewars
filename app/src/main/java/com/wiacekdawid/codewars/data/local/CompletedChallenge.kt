package com.wiacekdawid.codewars.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by dawidwiacek on 01/05/2018.
 */

@Entity(foreignKeys = [(ForeignKey(entity = Member::class,
        parentColumns = arrayOf("userName"),
        childColumns = arrayOf("userName"),
        onDelete = ForeignKey.CASCADE))], tableName = "completed_challenge_table")
data class CompletedChallenge(
        @PrimaryKey
        @NonNull
        val uid: String,
        val name: String?,
        val userName: String)

