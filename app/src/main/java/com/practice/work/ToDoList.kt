package com.practice.work

import java.util.*

/**
 * список задач
 */
object ToDoList {
    var data: MutableList<ToDoItem> = mutableListOf()
    fun getItemById(id: UUID): ToDoItem? {
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
        data.add(i)
    }
}