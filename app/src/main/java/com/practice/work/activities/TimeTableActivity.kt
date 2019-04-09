package com.practice.work.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_time_table.*
import java.util.*
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import com.practice.work.R
import com.practice.work.model.Task


//код для сохранения расписания, и тогда отсюда для уведомлений брать и чекать пустые записи
class TimeTableActivity : AppCompatActivity() {
    val myPrefs = "myPrefs"

    companion object {
        var tasks: MutableMap<String, Task> = mutableMapOf()
        fun initTask(){
            for(i in 8..22)
                tasks[i.toString()] = Task(i.toString())
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        val cal = Calendar.getInstance()
        val mMonth = cal.get(Calendar.MONTH)+1
        val mDay = cal.get(Calendar.DAY_OF_MONTH)
        val str = "$mDay.$mMonth"  //case for month
        toolbar.title=str
            setSupportActionBar(toolbar)

        tasks.forEach{ task->
            loadText(task.value.view, task.value.key)
        }

        initTask()
        tasks["8"]!!.view=findViewById(R.id.task8)
        tasks["9"]!!.view=findViewById(R.id.task9)
        tasks["10"]!!.view=findViewById(R.id.task10)
        tasks["11"]!!.view=findViewById(R.id.task11)
        tasks["12"]!!.view=findViewById(R.id.task12)
        tasks["13"]!!.view=findViewById(R.id.task13)
        tasks["14"]!!.view=findViewById(R.id.task14)
        tasks["15"]!!.view=findViewById(R.id.task15)
        tasks["16"]!!.view=findViewById(R.id.task16)
        tasks["17"]!!.view=findViewById(R.id.task17)
        tasks["18"]!!.view=findViewById(R.id.task18)
        tasks["19"]!!.view=findViewById(R.id.task19)
        tasks["20"]!!.view=findViewById(R.id.task20)
        tasks["21"]!!.view=findViewById(R.id.task21)
        tasks["22"]!!.view=findViewById(R.id.task22)

            // updateTaskText()
//это строковые переменные, в которых хранятся таски для расписания по времени


        fab.setOnClickListener {
            //реализация сохранения
            tasks.forEach{ task->
                saveText(task.value.view, task.value.key)
            }
            onBackPressed()
        }
    }

private fun updateTaskText(){
    tasks.forEach{ task->
        saveText(task.value.view, task.value.key)
       // task.value.text = loadText(task.value.view, task.value.key)!!
    }
    Log.i("NOTIFY", "Tasks text updated")
}

private fun saveText(etText:EditText, task:String) {
    val sPref: SharedPreferences = getSharedPreferences(myPrefs , Context.MODE_PRIVATE)
    val tasker = etText.text.toString()
    if(tasker != "") {
        etText.isEnabled = false
        etText.isCursorVisible = false
        etText.setBackgroundColor(Color.TRANSPARENT)
        etText.keyListener = null
    }
    val ed = sPref.edit()
    ed.putString(task, tasker)
    ed.apply()
    Log.i("SAVE", "Tasks text save")

}

    private fun loadText(etText:EditText, task:String): String? {
        val sPref: SharedPreferences = getSharedPreferences(myPrefs,Context.MODE_PRIVATE)
        val saveText = sPref.getString(task,"")

            etText.setText(saveText)
        Log.i("LOAD", "Tasks text load")
        return saveText

    }
    override fun onDestroy() {
        super.onDestroy()
        updateTaskText()
    }

}
