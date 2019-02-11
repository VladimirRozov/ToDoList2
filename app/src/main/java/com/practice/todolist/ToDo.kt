package com.practice.todolist

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.SimpleCursorAdapter




class ToDo : ListActivity() {
    private lateinit var mDbAdapter: DBAdapter
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDbAdapter = DBAdapter(this)
        mDbAdapter.open()
        fillData()
        registerForContextMenu(listView)
    }

    fun create() {
        val i = Intent(this, NewTask::class.java)
        startActivityForResult(i, ACTIVITY_CREATE)
    }

    fun fillData() {
        val c = this.mDbAdapter.fetchAllTasks()
        startManagingCursor(c)
        val from = arrayOf(DBAdapter.KEY_TASK, DBAdapter.KEY_DESCRIPTION)
        val to = intArrayOf(R.id.tv_item_text)
        val adapter = SimpleCursorAdapter(this, R.layout.row_items, c, from, to)
        listAdapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        fillData()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            DELETE_ID -> {
                val info = item.menuInfo as AdapterContextMenuInfo
                mDbAdapter.deleteTask(info.id)
                fillData()
                return true
            }
        }
        return super.onContextItemSelected(item)


    }


    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenuInfo
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
       // menu.add(0, DELETE_ID, 0, R.string.delete)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        //menu.add(0, INSERT_ID, 0, R.string.add)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            INSERT_ID -> {
                create()
                return true
            }
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val i = Intent(this, NewTask::class.java)
        i.putExtra(DBAdapter.KEY_ROW_ID, id)
        startActivityForResult(i, ACTIVITY_EDIT)
    }

    companion object {
        private val ACTIVITY_CREATE = 0
        private val ACTIVITY_EDIT = 1

        val INSERT_ID = Menu.FIRST
        val DELETE_ID = Menu.FIRST + 1
    }

}