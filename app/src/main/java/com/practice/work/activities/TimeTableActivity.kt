package com.practice.work.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_time_table.*
import java.util.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.practice.work.R
import com.practice.work.model.Task
import kotlinx.android.synthetic.main.activity_main.*


//код для сохранения расписания, и тогда отсюда для уведомлений брать и чекать пустые записи
class TimeTableActivity : AppCompatActivity() {
    private val myTimeTable = "myTimeTable"

    companion object {
        var tasks: MutableMap<String, Task> = mutableMapOf()
        fun initTask(){
            for(i in 8..22)
                tasks["tasks$i"] = Task("task$i")//rewrite
        }
    }

    private val m1OnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this,MainActivity::class.java)
                startActivityForResult(intent, 0)
            }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, TimeTableActivity::class.java)
                startActivityForResult(intent, 0)
            }
            R.id.navigation_notifications -> {
                val intent = Intent(this, TaskActivity::class.java)
                startActivityForResult(intent, 0)
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        val cal = Calendar.getInstance()
        val mMonth = cal.get(Calendar.MONTH)+1
        val mDay = cal.get(Calendar.DAY_OF_MONTH)
        val month = getMonth(mMonth)
        val str = "$mDay $month"  //case for month
        toolbar.title=str
            setSupportActionBar(toolbar)

        tasks.forEach{ task->
            loadText(task.value.view, task.value.keyText) //rewrite
        }
        navigation1.setOnNavigationItemSelectedListener(m1OnNavigationItemSelectedListener)
        initTask()
        tasks["tasks8"]!!.view=findViewById(R.id.task8)
        tasks["tasks9"]!!.view=findViewById(R.id.task9)
        tasks["tasks10"]!!.view=findViewById(R.id.task10)
        tasks["tasks11"]!!.view=findViewById(R.id.task11)
        tasks["tasks12"]!!.view=findViewById(R.id.task12)
        tasks["tasks13"]!!.view=findViewById(R.id.task13)
        tasks["tasks14"]!!.view=findViewById(R.id.task14)
        tasks["tasks15"]!!.view=findViewById(R.id.task15)
        tasks["tasks16"]!!.view=findViewById(R.id.task16)
        tasks["tasks17"]!!.view=findViewById(R.id.task17)
        tasks["tasks18"]!!.view=findViewById(R.id.task18)
        tasks["tasks19"]!!.view=findViewById(R.id.task19)
        tasks["tasks20"]!!.view=findViewById(R.id.task20)
        tasks["tasks21"]!!.view=findViewById(R.id.task21)
        tasks["tasks22"]!!.view=findViewById(R.id.task22)

             updateTaskText()
//это строковые переменные, в которых хранятся таски для расписания по времени


        fab.setOnClickListener {
            //реализация сохранения
            tasks.forEach{ task->
                saveText(task.value.view, task.value.keyText) //rewrite
            }
            onBackPressed()
        }
    }

private fun updateTaskText(){
    tasks.forEach{ task->
     //   saveText(task.value.view, task.value.keyText) //rewrite
        task.value.text = loadText(task.value.view, task.value.keyText)!! //rewrite
    }
    Log.i("NOTIFY", "Tasks text updated")
}

    private fun updateTasks(){
        tasks.forEach{ task->
               saveText(task.value.view, task.value.keyText) //rewrite
          //  task.value.text = loadText(task.value.view, task.value.keyText)!! //rewrite
        }
        Log.i("NOTIFY", "Tasks text updated")
    }


    private fun saveText(etText:EditText, task:String) {
    val sPref: SharedPreferences = getSharedPreferences(myTimeTable , Context.MODE_PRIVATE)
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
        val sPref: SharedPreferences = getSharedPreferences(myTimeTable,Context.MODE_PRIVATE)
        val saveText = sPref.getString(task,"")

            etText.setText(saveText)
        Log.i("LOAD", "Tasks text load")
        return saveText
    }

    override fun onDestroy() {
        super.onDestroy()
        updateTasks()
    }

    fun getMonth(month:Int): String{

        var monthStr = ""

        when(month){
            1 -> monthStr = "января"
            2 -> monthStr = "февраля"
            3 -> monthStr = "марта"
            4 -> monthStr = "япреля"
            5 -> monthStr = "мая"
            6 -> monthStr="июня"
            7 -> monthStr="июля"
            8 -> monthStr="августа"
            9 -> monthStr="сентября"
            10 -> monthStr="октября"
            11 -> monthStr="ноября"
            12 -> monthStr="декабря"
        }

        return monthStr
    }

}
