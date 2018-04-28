package com.wiacekdawid.codewars.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Entity(tableName = "member_table")
data class Member(
        @PrimaryKey
        @NonNull
        val uid: String,
        val username: String?,
        val name: String?)