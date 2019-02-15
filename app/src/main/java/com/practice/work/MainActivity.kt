package com.practice.work


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.support.v7.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    companion object {
        private val ADD_TASK_REQUEST = 0
    }

    lateinit var mDbAdapter: DBAdapter

    private var adapter: ItemAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        NotificationManager.createNotificationChannel(this)

        mDbAdapter = DBAdapter(this)  //DB connection
        mDbAdapter.open()
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton  //добавляет новую задачу
        fab.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }

        //следующий код до конца класса отвечает за вывод списка задач
        recyclerView = findViewById(R.id.to_do_list_container)
        recyclerView!!.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?


        updateUI()

    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        mDbAdapter.fillToDoList()
        ToDoList.sort()
        if (adapter == null) {
            adapter = ItemAdapter(ToDoList.data)
            recyclerView!!.adapter = adapter
        } else
            adapter!!.notifyDataSetChanged()
    }

    private inner class ItemHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.to_do_row, parent, false)) {

        private var titleItemTextView: TextView = itemView.findViewById(R.id.item_text_view)
        private var descriptionItemTextView: TextView = itemView.findViewById(R.id.description_text_view)
        private var dateItemTextView: TextView = itemView.findViewById(R.id.date_text_view)

        private lateinit var item: ToDoItem
        fun bind(i: ToDoItem) {
            item = i
            titleItemTextView.text = item.name
            descriptionItemTextView.text = item.description
            dateItemTextView.text = item.getDateAsString()
        }
    }

    private inner class ItemAdapter(val items: List<ToDoItem>) : RecyclerView.Adapter<ItemHolder>() {
        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.bind(ToDoList.data[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemHolder {
            return ItemHolder(LayoutInflater.from(this@MainActivity), parent)
        }
    }
}