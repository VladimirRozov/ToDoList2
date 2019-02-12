package com.practice.todolist

import java.util.*

/**
 * список задач
 */
object toDoList {
    var data: MutableList<ToDoItem> = mutableListOf()
    init {
        data.add(ToDoItem())
        data.add(ToDoItem())

    }

    fun getItemById(id: UUID): ToDoItem? {
        for (item in data) {
            if (item.id == id) {
                return item
            }
        }
        return null
    }
}