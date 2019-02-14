package com.practice.work

import java.util.*

/**
 * по идее модель одной задачи, которая отвечает за ее поведение
 */
class ToDoItem(var name: String){
    var description = ""
    val id = UUID.randomUUID()
    var date  = Date()

}