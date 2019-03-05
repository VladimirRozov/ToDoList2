package com.practice.work

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.support.v7.app.AlertDialog
import java.util.*
import android.content.Intent

/**
 * обработчик формы по созданию задания и сохранение в БД
 */

@Suppress("DEPRECATION")
internal class NewTaskActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mDbAdapter: DBAdapter

    private lateinit var btnDatePicker: Button
    private lateinit var btnTimePicker: Button
    private lateinit var txtDate: EditText
    private lateinit var txtTime: EditText
    private lateinit var mTask: EditText
    private lateinit var mDesc: EditText
    private lateinit var mAdd: Button
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var mRowId: Long? = null
    private var milisec: Long = 0
    var id:Long = 0

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
        mAdd.isEnabled = false
        val edList = arrayOf(mTask, mDesc, txtDate, txtTime)
        val textWatcher = CustomTextWatcher(edList, mAdd)
        for (editText in edList) editText.addTextChangedListener(textWatcher)
        mDbAdapter = DBAdapter(this)  //DB connection
        mDbAdapter.open()
            // может тут поковыряться с отменой
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

        when (v) {
            btnDatePicker ->
                // вызываем диалог с выбором даты
                callDatePicker()

            btnTimePicker ->
                // вызываем диалог с выбором времени
                callTimePicker()
        }
    }

    private fun callTimePicker() {
        // получаем текущее время
        val cal = Calendar.getInstance()
        mHour = cal.get(Calendar.HOUR_OF_DAY)
        mMinute = cal.get(Calendar.MINUTE)

        // инициализируем диалог выбора времени текущими значениями
        val timePickerDialog = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val editTextTimeParam = "$hourOfDay : $minute"
                txtTime.setText(editTextTimeParam)
            }, mHour, mMinute, false
        )
        timePickerDialog.show()
    }

    private fun callDatePicker() {
        // получаем текущую дату
        val cal = Calendar.getInstance()
        mYear = cal.get(Calendar.YEAR)
        mMonth = cal.get(Calendar.MONTH)
        mDay = cal.get(Calendar.DAY_OF_MONTH)

        // инициализируем диалог выбора даты текущими значениями
        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val editTextDateParam = dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year
                txtDate.setText(editTextDateParam)
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }


    private fun getTaskTimeAsLong(): Long{
        val time =((((mYear - 1970) * 365.24 + (mMonth) * 30.44 + (mDay)) * 86400 + (mHour) * 3600 + (mMinute) * 60-8*3600+12*60) * 1000)
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
//            txtDate.setText(
//                c.getString(
//                c.getColumnIndexOrThrow(DBAdapter.KEY_DATE)
//            ))
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
        milisec =   ((((mYear - 1970) * 365.24 + (mMonth) * 30.44 + (mDay)) * 86400 + (mHour) * 3600 + (mMinute) * 60-8*3600+12*60) * 1000).toLong()
//        print("русский"+milisec)
        val time = milisec

        //NotificationManager.setNotification(this, time, task)
        id = ToDoList.newItem(task, desc, (Math.random()*10000).toLong(), time)
        //вот тут было дублирование!
       // mDbAdapter.write()

    }

    override fun onBackPressed() = AlertDialog.Builder(this)
        .setTitle("Отменить создание задачи")
        .setMessage("Вы действительно хотите выйти?")
        .setNegativeButton(android.R.string.no, null)
        .setPositiveButton(android.R.string.yes) { _, _ ->
            ToDoList.delete(id)
            mDbAdapter.deleteTask(id)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }.create().show()
}
