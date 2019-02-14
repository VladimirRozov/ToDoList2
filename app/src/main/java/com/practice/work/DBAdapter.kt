package com.practice.work

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

/**
 * отвечает за функции по обращению к бд для сохранения данных
 */
class DBAdapter(private var mCtx: Context) {

    private lateinit var mDb: SQLiteDatabase
    private lateinit var mDbHelper: DBHelper

    @Throws(SQLException::class)
    fun open(): DBAdapter {
        mDbHelper = DBHelper(mCtx)
        mDb = mDbHelper.writableDatabase
        return this
    }

    fun close() {
        mDbHelper.close()
    }

    fun createTask(task: String, description: String, time: Long): Long {
        val initialValues = ContentValues()
        initialValues.put(KEY_TASK, task)
        initialValues.put(KEY_DESCRIPTION, description)
        //  initialValues.put(KEY_DATE, date)
        initialValues.put(KEY_TIME, time)
        return mDb.insert(DATABASE_TABLE, null, initialValues)
    }

    fun deleteTask(id: Long): Boolean {
        return mDb.delete(DATABASE_TABLE, "$KEY_ROW_ID = $id", null) > 0
    }

    fun updateTask(id: Long, task: String, description: String, time: Long): Boolean {
        val initialValues = ContentValues()
        initialValues.put(KEY_TASK, task)
        initialValues.put(KEY_DESCRIPTION, description)
        //initialValues.put(KEY_DATE, date)
        initialValues.put(KEY_TIME, time)
        return mDb.update(DATABASE_TABLE, initialValues, "$KEY_ROW_ID = $id", null) > 0
    }

    fun fetchAllTasks(): Cursor {
        return mDb.query(DATABASE_TABLE, arrayOf(KEY_ROW_ID, KEY_TASK, KEY_DESCRIPTION,  KEY_TIME), null, null, null, null, null)
    }

    fun fetchTask(id: Long): Cursor? {
        val c = mDb.query(
            DATABASE_TABLE,
            arrayOf(KEY_ROW_ID, KEY_TASK, KEY_DESCRIPTION,  KEY_TIME),
            "$KEY_ROW_ID = $id",
            null,
            null,
            null,
            null
        )
        c?.moveToFirst()
        return c
    }

    companion object {
        private val DATABASE_TABLE = "task_data"
        val KEY_ROW_ID = "_id"
        val KEY_TASK = "task"
        val KEY_DESCRIPTION = "description"
        //      val KEY_DATE = "date"
        val KEY_TIME = "time"
    }
}