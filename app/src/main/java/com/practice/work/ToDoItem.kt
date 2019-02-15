package com.practice.work

import java.util.*
import java.text.SimpleDateFormat


/**
 * по идее модель одной задачи, которая отвечает за ее поведение
 */
class ToDoItem(var name: String, var millisec: Long): Comparable<ToDoItem>{
    override fun compareTo(other: ToDoItem): Int {
        return date.compareTo(other.date)
    }


    var description = ""
    val id = UUID.randomUUID()
    var date  = Date(millisec)

    fun getDateAsString(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        val calendar = GregorianCalendar(TimeZone.getTimeZone("Europe/Moscow"))
        calendar.timeInMillis = millisec

        return sdf.format(calendar.time)
    }

}