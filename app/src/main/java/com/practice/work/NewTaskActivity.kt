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
import android.util.Log
import com.practice.work.activities.MainActivity
import com.practice.work.model.ToDoList

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
//    private var mYear: Int = 0
//    private var mMonth: Int = 0
//    private var mDay: Int = 0
//    private var mHour: Int = 0
//    private var mMinute: Int = 0
    val calendarNow = Calendar.getInstance()
    var calendarForSave = Calendar.getInstance()
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
        // инициализируем диалог выбора времени текущими значениями
        val timePickerDialog = TimePickerDialog(this,

            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val editTextTimeParam = "$hourOfDay : $minute"
                txtTime.setText(editTextTimeParam)
                calendarForSave.set(Calendar.HOUR, hourOfDay)
                calendarForSave.set(Calendar.MINUTE, minute)
            },

            calendarNow.get(Calendar.HOUR), calendarNow.get(Calendar.MINUTE), true
        )
        timePickerDialog.show()
    }

    private fun callDatePicker() {

        // инициализируем диалог выбора даты текущими значениями
        val datePickerDialog = DatePickerDialog(this,

            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val editTextDateParam = dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year
                txtDate.setText(editTextDateParam)
                calendarForSave.set(year, monthOfYear, dayOfMonth)
            },

            calendarNow.get(Calendar.YEAR),
            calendarNow.get(Calendar.MONTH),
            calendarNow.get(Calendar.DAY_OF_MONTH)

        )


        datePickerDialog.show()
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
            txtTime.setText(
                c.getString(
                    c.getColumnIndexOrThrow(DBAdapter.KEY_TIME)
                ))

        }
    }

    override fun onPause() {
        super.onPause()
        saveState()
        mDbAdapter.write()
    }

    override fun onResume() {
        super.onResume()
        populateFields()
        mDbAdapter.write()
    }

    override fun onStop() {
        super.onStop()
        mDbAdapter.write()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState()
        outState.putSerializable(DBAdapter.KEY_ROW_ID, mRowId)
    }

    private fun saveState() {
        val task = mTask.text.toString()
        val desc = mDesc.text.toString()

        //устанавливаем время в милисекундах
        milisec = calendarForSave.timeInMillis

        //создаем новую задачу, которая отражается в списке
        id = ToDoList.newItem(task, desc, (Math.random()*10000).toLong(), milisec)

        Log.i("TIME", "time saved $milisec")

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
