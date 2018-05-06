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
        var userName: String = DEFAULT_USER_NAME,
        var name: String? = null,
        var rank: Int = DEFAULT_RANK) {
        companion object {
                const val DEFAULT_USER_NAME = "DEFAULT_USER_NAME"
                const val DEFAULT_RANK = 0
        }
}