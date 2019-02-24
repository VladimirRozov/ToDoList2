package com.practice.work

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_time_table.*
import java.util.*
import android.widget.Toast
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color


//код для сохранения расписания, и тогда отсюда для уведомлений брать и чекать пустые записи
class TimeTableActivity : AppCompatActivity() {
    val myPrefs8 = "myprefs"
    val myPrefs9 = "myprefs"
    val myPrefs10 = "myprefs"
    val myPrefs11 = "myprefs"
    val myPrefs12 = "myprefs"
    val myPrefs13 = "myprefs"
    val myPrefs14 = "myprefs"
    val myPrefs15 = "myprefs"
    val myPrefs16 = "myprefs"
    val myPrefs17 = "myprefs"
    val myPrefs18 = "myprefs"
    val myPrefs19 = "myprefs"
    val myPrefs20 = "myprefs"
    val myPrefs21 = "myprefs"
    val myPrefs22 = "myprefs"

    var task8 = "task8"
    var task9 = "task9"
    var task10 = "task10"
    var task11 = "task11"
    var task12 = "task12"
    var task13 = "task13"
    var task14 = "task14"
    var task15 = "task15"
    var task16 = "task16"
    var task17 = "task17"
    var task18 = "task18"
    var task19 = "task19"
    var task20 = "task20"
    var task21 = "task21"
    var task22 = "task22"

    lateinit var record8:EditText
    lateinit var record9:EditText
    lateinit var record10:EditText
    lateinit var record11:EditText
    lateinit var record12:EditText
    lateinit var record13:EditText
    lateinit var record14:EditText
    lateinit var record15:EditText
    lateinit var record16:EditText
    lateinit var record17:EditText
    lateinit var record18:EditText
    lateinit var record19:EditText
    lateinit var record20:EditText
    lateinit var record21:EditText
    lateinit var record22:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        val cal = Calendar.getInstance()
        val mMonth = cal.get(Calendar.MONTH)+1
        val mDay = cal.get(Calendar.DAY_OF_MONTH)
        val str = "$mDay.$mMonth"
        toolbar.title=str
            setSupportActionBar(toolbar)
        record8=findViewById(R.id.task8)
        record9=findViewById(R.id.task9)
        record10=findViewById(R.id.task10)
        record11=findViewById(R.id.task11)
        record12=findViewById(R.id.task12)
        record13=findViewById(R.id.task13)
        record14=findViewById(R.id.task14)
        record15=findViewById(R.id.task15)
        record16=findViewById(R.id.task16)
        record17=findViewById(R.id.task17)
        record18=findViewById(R.id.task18)
        record19=findViewById(R.id.task19)
        record20=findViewById(R.id.task20)
        record21=findViewById(R.id.task21)
        record22=findViewById(R.id.task22)


        loadText(record8, task8, myPrefs8)
        loadText(record9, task9,myPrefs9)
        loadText(record10, task10,myPrefs10)
        loadText(record11, task11,myPrefs11)
        loadText(record12, task12,myPrefs12)
        loadText(record13, task13,myPrefs13)
        loadText(record14, task14,myPrefs14)
        loadText(record15, task15,myPrefs15)
        loadText(record16, task16,myPrefs16)
        loadText(record17, task17,myPrefs17)
        loadText(record18,task18, myPrefs18)
        loadText(record19, task19,myPrefs19)
        loadText(record20, task20,myPrefs20)
        loadText(record21, task21,myPrefs21)
        loadText(record22,task22, myPrefs22)

        fab.setOnClickListener {
            //реализация сохранения
            saveText(record8, task8, myPrefs8)
            saveText(record9, task9, myPrefs9)
            saveText(record10, task10, myPrefs10)
            saveText(record11, task11, myPrefs11)
            saveText(record12, task12, myPrefs12)
            saveText(record13, task13, myPrefs13)
            saveText(record14, task14, myPrefs14)
            saveText(record15, task15, myPrefs15)
            saveText(record16, task16, myPrefs16)
            saveText(record17, task17, myPrefs17)
            saveText(record18, task18, myPrefs18)
            saveText(record19, task19, myPrefs19)
            saveText(record20, task20, myPrefs20)
            saveText(record21, task21, myPrefs21)
            saveText(record22, task22, myPrefs22)
            onBackPressed()
        }
    }

    private fun saveText(etText:EditText, task:String, pref: String) {
        val sPref: SharedPreferences = getSharedPreferences(pref , Context.MODE_PRIVATE)
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
    }

    private fun loadText(etText:EditText, task:String, pref:String) {
        val sPref: SharedPreferences = getSharedPreferences(pref,Context.MODE_PRIVATE)
        val saveText = sPref.getString(task,"")

        if (saveText != "") {
//            etText.isEnabled = false
//            etText.isCursorVisible = false
//            etText.setBackgroundColor(Color.TRANSPARENT)
//            etText.keyListener = null
            etText.setText(saveText)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveText(record8, task8, myPrefs8)
        saveText(record9, task9, myPrefs9)
        saveText(record10, task10, myPrefs10)
        saveText(record11, task11, myPrefs11)
        saveText(record12, task12, myPrefs12)
        saveText(record13, task13, myPrefs13)
        saveText(record14, task14, myPrefs14)
        saveText(record15, task15, myPrefs15)
        saveText(record16, task16, myPrefs16)
        saveText(record17, task17, myPrefs17)
        saveText(record18, task18, myPrefs18)
        saveText(record19, task19, myPrefs19)
        saveText(record20, task20, myPrefs20)
        saveText(record21, task21, myPrefs21)
        saveText(record22, task22, myPrefs22)
    }

}
