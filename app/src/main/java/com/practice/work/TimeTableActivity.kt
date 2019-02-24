package com.practice.work

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_time_table.*
import java.util.*

//код для сохранения расписания, и тогда отсюда для уведомлений брать и чекать пустые записи
class TimeTableActivity : AppCompatActivity() {

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
    lateinit var time8:TextView
    lateinit var time9:TextView
    lateinit var time10:TextView
    lateinit var time11:TextView
    lateinit var time12:TextView
    lateinit var time13:TextView
    lateinit var time14:TextView
    lateinit var time15:TextView
    lateinit var time16:TextView
    lateinit var time17:TextView
    lateinit var time18:TextView
    lateinit var time19:TextView
    lateinit var time20:TextView
    lateinit var time21:TextView
    lateinit var time22:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        val cal = Calendar.getInstance()
        val mMonth = cal.get(Calendar.MONTH)+1
        val mDay = cal.get(Calendar.DAY_OF_MONTH)
        val str = "$mDay.$mMonth"
        toolbar.title=str
            setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            //реализация сохранения
            save()
            onBackPressed()
        }
    }

   fun save(){
       record8=findViewById(R.id.task8)
       println(record8.toString())
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
       time8=findViewById<EditText>(R.id.time8)
       time9=findViewById<EditText>(R.id.time9)
       time10=findViewById<EditText>(R.id.time10)
       time11=findViewById<EditText>(R.id.time11)
       time12=findViewById<EditText>(R.id.time12)
       time13=findViewById<EditText>(R.id.time13)
       time14=findViewById<EditText>(R.id.time14)
       time15=findViewById<EditText>(R.id.time15)
       time16=findViewById<EditText>(R.id.time16)
       time17=findViewById<EditText>(R.id.time17)
       time18=findViewById<EditText>(R.id.time18)
       time19=findViewById<EditText>(R.id.time19)
       time20=findViewById<EditText>(R.id.time20)
       time21=findViewById<EditText>(R.id.time21)
       time22=findViewById<EditText>(R.id.time22)
    }

}
