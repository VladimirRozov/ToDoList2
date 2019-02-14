package com.practice.todolist

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.support.v4.app.NotificationCompat
import android.app.PendingIntent
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.support.v4.app.NotificationManagerCompat
import android.widget.ListView
import android.widget.SimpleCursorAdapter


class MainActivity : AppCompatActivity() {
    companion object {
        private val ADD_TASK_REQUEST = 0
    }

    var lvData: ListView? = null
    var scAdapter: SimpleCursorAdapter? = null
    lateinit var mDbAdapter: DBAdapter
    lateinit var cursor: Cursor
    private var adapter: ItemAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mDbAdapter = DBAdapter(this)  //DB connection
        mDbAdapter.open()
        //создание курсора и извление данных и бд
        cursor = mDbAdapter.fetchAllTasks()
        startManagingCursor(cursor)
        val from = arrayOf<String>(DBAdapter.KEY_TASK, DBAdapter.KEY_TASK)
        val to = intArrayOf(R.id.ivImg, R.id.item_text_view)

        // создааем адаптер и настраиваем список
        scAdapter = SimpleCursorAdapter(this, R.layout.to_do_row, cursor, from, to)
        lvData = findViewById<ListView>(R.id.to_do_list_container)
        lvData!!.setAdapter(scAdapter)
        registerForContextMenu(lvData)
        createNotificiationChannel()

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton  //добавляет новую задачу
        fab.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }

        //следующий код до конца класса отвечает за вывод списка задач
        //    recyclerView = findViewById(R.id.to_do_list_container)
        //  recyclerView?.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        //       updateUI()

        //ха, а вот и не весь
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
    }

    override fun onResume() {
        super.onResume()
        //   updateUI()
    }

    private fun updateUI(){
        if (adapter==null) {
            adapter = ItemAdapter(toDoList.data)
            recyclerView!!.adapter = adapter
        }else
            adapter!!.notifyDataSetChanged()
    }

    private inner class ItemHolder(inflater: LayoutInflater, parent: ViewGroup): View.OnClickListener,
        RecyclerView.ViewHolder(inflater.inflate(R.layout.to_do_row, parent, false)){
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
//            Toast.makeText(this@MainActivity,
//                item.name + " clicked!", Toast.LENGTH_SHORT)
//                .show()
            makeNotification()
        }
        private var titleItemTextView: TextView = itemView.findViewById(R.id.item_text_view)
        private lateinit var item: ToDoItem
        fun bind(i:ToDoItem){
            item = i
            titleItemTextView.text = item.name
        }
    }

    private inner class ItemAdapter(val items: List<ToDoItem>): RecyclerView.Adapter<ItemHolder>() {
        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.bind(toDoList.data[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemHolder {
            return ItemHolder(LayoutInflater.from(this@MainActivity), parent)
        }
    }

    //пусть пока здесь побудет, но вообще лучше убрать
    fun makeNotification(){
        val notificationIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val builder = NotificationCompat.Builder(this, DEADLINE_NOTIFICATION_CHANNEL_ID)

        builder.setContentIntent(contentIntent)
            // обязательные настройки
            .setSmallIcon(R.drawable.abc_btn_borderless_material)
            .setContentTitle("Завтра вам будет больно")
            .setContentText("*описание причины боли*") // Текст уведомления
            // необязательные настройки
            .setTicker("Последнее китайское предупреждение!")
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(true)


        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManager.notify(1, builder.build())
    }

    fun createNotificiationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "deadlines"
            val descriptionText = "AAAAAAA"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(DEADLINE_NOTIFICATION_CHANNEL_ID, name, importance)
            mChannel.description = descriptionText

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
    private val DEADLINE_NOTIFICATION_CHANNEL_ID = "666"




}
