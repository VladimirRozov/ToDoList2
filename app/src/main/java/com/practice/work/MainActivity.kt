package com.practice.work


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.support.v7.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    companion object {
        private const val ADD_TASK_REQUEST = 0
    }

    lateinit var mDbAdapter: DBAdapter

    private var adapter: ItemAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var isStopApp = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        isStopApp = true
        setContentView(R.layout.activity_main)

        //DB connection
        mDbAdapter = DBAdapter(this)

        //кнопка добавляет новую задачу
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            isStopApp = false
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }

        //view отвечающая за список
        recyclerView = findViewById(R.id.to_do_list_container)
        recyclerView!!.layoutManager = LinearLayoutManager(this)


        updateUI()

    }

//    override fun onStop() {
//        super.onStop()
//    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        mDbAdapter.read()
        ToDoList.sort()
        if (adapter == null) {
            adapter = ItemAdapter(ToDoList.data)
            recyclerView!!.adapter = adapter
        } else
            adapter!!.notifyDataSetChanged()
    }

    private inner class ItemHolder(inflater: LayoutInflater, parent: ViewGroup) : View.OnClickListener,
        RecyclerView.ViewHolder(inflater.inflate(R.layout.to_do_row, parent, false)) {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Удаление")
                .setMessage("Вы действительно хотите удалить эту задачу?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    ToDoList.delete(id)
                    mDbAdapter.deleteTask(id)
                    updateUI()
                }.create().show()

        }

        private var titleItemTextView: TextView = itemView.findViewById(R.id.item_text_view)
        private var descriptionItemTextView: TextView = itemView.findViewById(R.id.description_text_view)
        private var dateItemTextView: TextView = itemView.findViewById(R.id.date_text_view)
        private var id: Long = 0

        private lateinit var item: ToDoItem
        fun bind(i: ToDoItem) {
            item = i
            titleItemTextView.text = item.name
            descriptionItemTextView.text = item.description
            dateItemTextView.text = item.getDateAsString()
            id = item.id
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
