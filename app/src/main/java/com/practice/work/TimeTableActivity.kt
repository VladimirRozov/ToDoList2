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

    companion object {
        var tasks: MutableMap<String, String?> = mutableMapOf()
    }
    private fun updateTasks(){
        tasks = mutableMapOf()
        tasks["8"] = textTask8
        tasks["9"] = textTask9
        tasks["10"] = textTask10
        tasks["11"] = textTask11
        tasks["12"] = textTask12
        tasks["13"] = textTask13
        tasks["14"] = textTask14
        tasks["15"] = textTask15
        tasks["16"] = textTask16
        tasks["17"] = textTask17
        tasks["18"] = textTask18
        tasks["19"] = textTask19
        tasks["20"] = textTask20
        tasks["21"] = textTask21
        tasks["22"] = textTask22
    }

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

    var textTask8: String? = null
    var textTask9: String? = null
    var textTask10: String? = null
    var textTask11: String? = null
    var textTask12: String? = null
    var textTask13: String? = null
    var textTask14: String? = null
    var textTask15 : String? = null
    var textTask16 : String? = null
    var textTask17 : String? = null
    var textTask18 : String? = null
    var textTask19 : String? = null
    var textTask20 : String? = null
    var textTask21 : String? = null
    var textTask22 : String? = null

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

//это тсроковые переменные, в которых хранятся таски для расписания по времени
        textTask8 = loadText(record8, task8, myPrefs8)
        textTask9 = loadText(record9, task9,myPrefs9)
        textTask10 = loadText(record10, task10,myPrefs10)
        textTask11 = loadText(record11, task11,myPrefs11)
        textTask12 = loadText(record12, task12,myPrefs12)
        textTask13 = loadText(record13, task13,myPrefs13)
        textTask14 = loadText(record14, task14,myPrefs14)
        textTask15 = loadText(record15, task15,myPrefs15)
        textTask16 = loadText(record16, task16,myPrefs16)
        textTask17 = loadText(record17, task17,myPrefs17)
        textTask18 = loadText(record18,task18, myPrefs18)
        textTask19 = loadText(record19, task19,myPrefs19)
        textTask20 = loadText(record20, task20,myPrefs20)
        textTask21 = loadText(record21, task21,myPrefs21)
        textTask22 = loadText(record22,task22, myPrefs22)

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

            updateTasks()
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

        updateTasks()
    }

    private fun loadText(etText:EditText, task:String, pref:String): String? {
        val sPref: SharedPreferences = getSharedPreferences(pref,Context.MODE_PRIVATE)
        val saveText = sPref.getString(task,"")

        if (saveText != "") {
//            etText.isEnabled = false
//            etText.isCursorVisible = false
//            etText.setBackgroundColor(Color.TRANSPARENT)
//            etText.keyListener = null
            etText.setText(saveText)
        }
        return saveText

        updateTasks()
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
