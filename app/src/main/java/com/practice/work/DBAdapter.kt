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

        const val DATABASE_TABLE_TASK = "task_data"
        const val KEY_ROW_ID = "_id"
        const val KEY_TASK = "task"
        const val KEY_DESCRIPTION = "description"
        const val KEY_TIME = "time"

        const val DATABASE_TABLE_PLAN = "plan_data"
        const val KEY_PLAN = "plan"
        const val KEY_START_TIME = "start"
        const val KEY_END_TIME = "end"
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

    fun createTask(task: ToDoItem): Long {
        val initialValues = ContentValues()
        initialValues.put(KEY_ROW_ID, task.id)
        initialValues.put(KEY_TASK, task.name)
        initialValues.put(KEY_DESCRIPTION, task.description)
        //  initialValues.put(KEY_DATE, date)
        initialValues.put(KEY_TIME, task.millisec)
        try {return mDb.insert(DATABASE_TABLE_TASK, null, initialValues)}
        catch (e: android.database.sqlite.SQLiteConstraintException){return -666}
    }

    fun createPlan(planItem: PlanItem): Long{
        val initialValues = ContentValues()
        initialValues.put(KEY_ROW_ID, planItem.id)
        initialValues.put(KEY_PLAN, planItem.name)
        initialValues.put(KEY_DESCRIPTION, planItem.description)
        //  initialValues.put(KEY_DATE, date)
        initialValues.put(KEY_START_TIME, planItem.timeStart)
        initialValues.put(KEY_END_TIME, planItem.timeEnd)
        initialValues.put(KEY_TASK, planItem.task?.id)
        try {return mDb.insert(DATABASE_TABLE_PLAN, null, initialValues)}
        catch (e: android.database.sqlite.SQLiteConstraintException){return -666}
    }

    fun deleteTask(id: Long): Boolean {
        return mDb.delete(DATABASE_TABLE_TASK, "$KEY_ROW_ID = $id", null) > 0
    }

    fun updateTask(id: Long, task: String, description: String, time: Long): Boolean {
        val initialValues = ContentValues()
        initialValues.put(KEY_TASK, task)
        initialValues.put(KEY_DESCRIPTION, description)
        //initialValues.put(KEY_DATE, date)
        initialValues.put(KEY_TIME, time)
        return mDb.update(DATABASE_TABLE_TASK, initialValues, "$KEY_ROW_ID = $id", null) > 0
    }

    fun fetchAllTasks(): Cursor {
        return mDb.query(DATABASE_TABLE_TASK, arrayOf(KEY_ROW_ID, KEY_TASK, KEY_DESCRIPTION,  KEY_TIME), null, null, null, null, null)
    }

    fun fetchTask(id: Long): Cursor? {
        val c = mDb.query(
            DATABASE_TABLE_TASK,
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