package com.wiacekdawid.codewars.data.local

import android.arch.persistence.room.RoomDatabase

/**
 * Created by dawidwiacek on 28/04/2018.
 */

abstract class DbDataSource : RoomDatabase() {
    abstract fun membersDao(): MemberDao
}