package com.practice.work


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.app.PendingIntent
import android.database.Cursor
import android.os.Build
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.AdapterView.AdapterContextMenuInfo
import android.view.ContextMenu.ContextMenuInfo
import android.view.ContextMenu
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        private val ADD_TASK_REQUEST = 0
    }

    private val CM_DELETE_ID = 1
    var lvData: ListView? = null
    var scAdapter: SimpleCursorAdapter? = null
    lateinit var mDbAdapter: DBAdapter
    lateinit var cursor: Cursor
    private var adapter: ItemAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        NotificationManager.createNotificationChannel(this)

        mDbAdapter = DBAdapter(this)  //DB connection
        mDbAdapter.open()
        //создание курсора и извление данных и бд
        cursor = mDbAdapter.fetchAllTasks()
        startManagingCursor(cursor)
        val from = arrayOf<String>(DBAdapter.KEY_TASK, DBAdapter.KEY_DESCRIPTION, DBAdapter.KEY_DATE, DBAdapter.KEY_TIME)
        val to = intArrayOf(R.id.item_text_view,R.id.item_text_decr,R.id.item_date, R.id.item_time)
        // создааем адаптер и настраиваем список
        scAdapter = SimpleCursorAdapter(this, R.layout.to_do_row, cursor, from, to)
        lvData = findViewById(R.id.to_do_list_container)
        lvData!!.setAdapter(scAdapter)
        registerForContextMenu(lvData)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton  //добавляет новую задачу
        fab.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }

        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI(){
        if (adapter==null) {
            adapter = ItemAdapter(toDoList.data)
//            recyclerView!!.adapter = adapter
        }else
            adapter!!.notifyDataSetChanged()
    }

    private inner class ItemHolder(inflater: LayoutInflater, parent: ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.to_do_row, parent, false)){

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



}
