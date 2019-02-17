package com.practice.work


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Запросы к БД
 */
class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DATABASE_CREATE)

    }

    override fun onUpgrade(db: SQLiteDatabase, arg1: Int, arg2: Int) {
        db.execSQL("DROP TABLE IF EXISTS task_data")
        onCreate(db)

    }

    companion object {
        private const val DATABASE_NAME = "tasks"
        private const val DATABASE_VERSION = 1
        private const val DATABASE_CREATE =
            "CREATE TABLE task_data (" +
                    "_id integer primary key autoincrement," +
                    "task text not null," +
                    "description text not null, " +
                    "time long not null);"
    }

}