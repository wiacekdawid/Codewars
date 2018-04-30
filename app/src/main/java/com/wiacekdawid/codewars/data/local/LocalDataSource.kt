package com.wiacekdawid.codewars.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by dawidwiacek on 28/04/2018.
 */

@Database(entities = [Member::class], version = 1)
abstract class LocalDataSource : RoomDatabase() {
    abstract fun membersDao(): MemberDao
}