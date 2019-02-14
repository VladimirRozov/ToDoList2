package com.practice.work

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.util.*

/**
 * обработчик формы по созданию задания и сохранение в БД
 */

internal class NewTaskActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mDbAdapter: DBAdapter

    lateinit var btnDatePicker: Button
    lateinit var btnTimePicker: Button
    lateinit var txtDate: EditText
    lateinit var txtTime: EditText
    private lateinit var mTask: EditText
    private lateinit var mDesc: EditText
    private lateinit var mAdd: Button

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var mRowId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_task)
        mTask = findViewById(R.id.txtTask)
        mDesc = findViewById(R.id.txtDesc)
        btnDatePicker = findViewById(R.id.btn_date)
        btnTimePicker = findViewById(R.id.btn_time)
        txtDate = findViewById(R.id.in_date)
        txtTime = findViewById(R.id.in_time)
        mAdd = findViewById(R.id.btnAdd)
        btnDatePicker.setOnClickListener(this)
        btnTimePicker.setOnClickListener(this)

        mDbAdapter = DBAdapter(this)  //DB connection
        mDbAdapter.open()

        mRowId = if (savedInstanceState == null)
            null
        else
            savedInstanceState.getSerializable(DBAdapter.KEY_ROW_ID) as Long
        if (mRowId == null) {
            val extras = intent.extras
            mRowId = extras?.getLong(DBAdapter.KEY_ROW_ID)
        }

        populateFields()
        mAdd.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onClick(v: View) {

        if (v === btnDatePicker) {

            // Get Current Date
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth -> txtDate.setText(
                    dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
        if (v === btnTimePicker) {

            // Get Current Time
            val c = Calendar.getInstance()
            mHour = c.get(Calendar.HOUR_OF_DAY)
            mMinute = c.get(Calendar.MINUTE)

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute -> txtTime.setText("$hourOfDay:$minute") }, mHour, mMinute, false
            )
            timePickerDialog.show()
        }
        NotificationManager.setNotification(this, getTaskTimeAsLong())
    }


    //нужно вот этот метод поменять
    private fun getTaskTimeAsLong(): Long{
        val time =( mYear*31556926+mMonth*2629743+mDay*86400+mHour*3600+mMinute*60)*1000
        return time.toLong()
    }
    private fun populateFields() {
        if (mRowId != null) {
            val c = mDbAdapter.fetchTask(mRowId!!)
                startManagingCursor(c)
            mTask.setText(
                c!!.getString(
                    c.getColumnIndexOrThrow(DBAdapter.KEY_TASK)
                )
            )
            mDesc.setText(
                c.getString(
                    c.getColumnIndexOrThrow(DBAdapter.KEY_DESCRIPTION)
                )
            )
            txtDate.setText(
                c.getString(
                c.getColumnIndexOrThrow(DBAdapter.KEY_DATE)
            ))
            txtTime.setText(
                c.getString(
                c.getColumnIndexOrThrow(DBAdapter.KEY_TIME)
            ))

        }
    }

    override fun onPause() {
        super.onPause()
        saveState()
    }

    override fun onResume() {
        super.onResume()
        populateFields()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState()
        outState.putSerializable(DBAdapter.KEY_ROW_ID, mRowId)
    }

    private fun saveState() {
        val task = mTask.text.toString()
        val desc = mDesc.text.toString()
        val date = txtDate.text.toString()
        val time = txtTime.text.toString()
        if (mRowId == null) {
            val id = mDbAdapter.createTask(task,desc,date,time)
            if (id > 0) {
                mRowId = id
            }
        } else {
            mDbAdapter.updateTask(mRowId!!,task,desc,date,time)
        }
    }


}