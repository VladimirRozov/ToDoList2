package com.practice.work

import java.util.*

/**
 * список задач
 */
object toDoList {
    var data: MutableList<ToDoItem> = mutableListOf()
    fun getItemById(id: UUID): ToDoItem? {
        for (item in data) {
            if (item.id == id) {
                return item
            }
        }
        return null
    }
}