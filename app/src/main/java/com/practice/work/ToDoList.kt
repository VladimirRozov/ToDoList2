package com.practice.work

import java.util.*

/**
 * список задач
 */
object ToDoList {
    var data: MutableList<ToDoItem> = mutableListOf()
//    init {
//        data.add(ToDoItem("1", 1234567))
//        data.add(ToDoItem("2", 12345))
//
//    }
    fun getItemById(id: Int): ToDoItem? {
        for (item in data) {
            if (item.id == id) {
                return item
            }
        }
        return null
    }

    fun sort(){
        data.sort()
    }

    fun add(i:ToDoItem){
        if(!data.contains(i)) {
            data.add(i)
        }
    }
}