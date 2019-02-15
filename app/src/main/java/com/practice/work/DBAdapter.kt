package com.practice.work

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import kotlin.Exception

/**
 * отвечает за функции по обращению к бд для сохранения данных
 */
class DBAdapter(private var mCtx: Context) {
    companion object {
        private val DATABASE_TABLE = "task_data"
        val KEY_ROW_ID = "_id"
        val KEY_TASK = "task"
        val KEY_DESCRIPTION = "description"
        //      val KEY_DATE = "date"
        val KEY_TIME = "time"
    }

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

    fun createTask(task: String, description: String, time: Long, id: Long): Long {
        val initialValues = ContentValues()
        initialValues.put(KEY_ROW_ID, id)
        initialValues.put(KEY_TASK, task)
        initialValues.put(KEY_DESCRIPTION, description)
        //  initialValues.put(KEY_DATE, date)
        initialValues.put(KEY_TIME, time)
        try {return mDb.insert(DATABASE_TABLE, null, initialValues)}
        catch (e: android.database.sqlite.SQLiteConstraintException){return -666}
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

    private fun mapToTask(c: Cursor): ToDoItem{
        try {
            val name = c.getString(
                c.getColumnIndex(KEY_TASK)
            )
            val time = c.getLong(
                c.getColumnIndex(KEY_TIME)
            )
            val desc = c.getString(
                c.getColumnIndex(KEY_DESCRIPTION)
            )
            val id = c.getLong(
                c.getColumnIndex(KEY_ROW_ID)
            )
            val item = ToDoItem(name, time, id)
            item.description = desc
            return item
        }catch (e: Exception){
            return ToDoItem("ой все", 123456789876, Math.random().toLong())
        }
    }
    fun fillToDoList(){
        val c = fetchAllTasks()
        while(c.moveToNext())
            ToDoList.add(mapToTask(c))
    }

    fun read(){
        open()
       // mDb.execSQL(DBHelper.DATABASE_CREATE)
        fillToDoList()
        Log.i("DB_STEP", "read db")

        // mDb.execSQL(DBHelper.DATABASE_DELETE)
        //close()
    }

    fun write(){
       // open()
       // mDb.execSQL(DBHelper.DATABASE_CREATE)
        ToDoList.commitToDB(this)
       // ToDoList.deleteAll()
        Log.i("DB_STEP", "write db")

    }
}