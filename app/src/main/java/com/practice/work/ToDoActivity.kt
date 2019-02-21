package com.practice.work

import android.annotation.SuppressLint
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.SimpleCursorAdapter

/**
 *  класс, отвечающий за обновления заданий (новых пунктов)
 */


@SuppressLint("Registered")
@Suppress("DEPRECATION")
class ToDoActivity : ListActivity() {
    private lateinit var mDbAdapter: DBAdapter

    companion object {
        private const val ACTIVITY_CREATE = 0
        private const val ACTIVITY_EDIT = 1

        const val INSERT_ID = Menu.FIRST
        const val DELETE_ID = Menu.FIRST + 1
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDbAdapter = DBAdapter(this)
        mDbAdapter.open()
        fillData()
        registerForContextMenu(listView)
    }

    private fun create() {
        val i = Intent(this, NewTaskActivity::class.java)
        startActivityForResult(i, ACTIVITY_CREATE)
    }

    private fun fillData() {
        val c = this.mDbAdapter.fetchAllTasks()
        startManagingCursor(c)
        val from = arrayOf(DBAdapter.KEY_TASK, DBAdapter.KEY_DESCRIPTION)
        val to = intArrayOf(R.id.item_text_view)
        val adapter = SimpleCursorAdapter(this, R.layout.to_do_row, c, from, to)
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
        val i = Intent(this, NewTaskActivity::class.java)
        i.putExtra(DBAdapter.KEY_ROW_ID, id)
        startActivityForResult(i, ACTIVITY_EDIT)
    }


}