package com.wiacekdawid.codewars.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by dawidwiacek on 01/05/2018.
 */

@Entity(tableName = "authored_challenge_table")
data class AuthoredChallenge(
        @PrimaryKey
        @NonNull
        val uid: String,
        val name: String?)