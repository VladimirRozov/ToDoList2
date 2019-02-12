package com.practice.todolist

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*

import kotlinx.android.synthetic.main.activity_main.*

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

        mDbAdapter = DBAdapter(this)  //DB connection
        mDbAdapter.open()

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton  //добавляет новую задачу
        fab.setOnClickListener {
            val intent = Intent(this, NewTask::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }

        //следующий код до конца класса отвечает за вывод списка задач
        recyclerView = findViewById(R.id.to_do_list_container)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        updateUI()
    }

    private fun updateUI(){
        adapter = ItemAdapter(toDoList.data)
        recyclerView!!.adapter = adapter
    }

    private inner class ItemHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.to_do_row, parent, false)) {
    }

    private inner class ItemAdapter(val items: List<ToDoItem>): RecyclerView.Adapter<ItemHolder>() {
        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(p0: ItemHolder, p1: Int) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemHolder {
            return ItemHolder(LayoutInflater.from(this@MainActivity), parent)
        }
    }
}
