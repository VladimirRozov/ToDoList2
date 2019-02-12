package com.practice.todolist

import java.util.*

/**
 * по идее модель одной задачи, которая отвечает за ее поведение
 */
class ToDoItem{
    var description = ""
    var name = ""
    val id = UUID.randomUUID()
    var date  = Date()

}