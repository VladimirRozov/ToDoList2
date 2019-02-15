package com.practice.work

import java.util.*
import java.text.SimpleDateFormat


/**
 * по идее модель одной задачи, которая отвечает за ее поведение
 */
class ToDoItem(var name: String, var millisec: Long, val id: Int): Comparable<ToDoItem>{
    override fun compareTo(other: ToDoItem): Int {
        return date.compareTo(other.date)
    }


    var description = ""
    var date  = Date(millisec)

    fun getDateAsString(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        val calendar = GregorianCalendar(TimeZone.getTimeZone("Europe/Moscow"))
        calendar.timeInMillis = millisec

        return sdf.format(calendar.time)
    }

    override fun equals(other: Any?): Boolean {
        try{
            val o: ToDoItem = other as ToDoItem
            return id==o.id
        }catch (e: Exception){
            return false
        }

    }

}