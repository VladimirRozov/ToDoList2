package com.practice.work

import android.annotation.SuppressLint
import java.util.*
import java.text.SimpleDateFormat


/**
 * по идее модель одной задачи, которая отвечает за ее поведение
 */
class ToDoItem(var name: String, var millisec: Long, val id: Long): Comparable<ToDoItem>{
    override fun compareTo(other: ToDoItem): Int {
        return date.compareTo(other.date)
    }


    var description = ""
    private var date  = Date(millisec)
    var duration = 1111
    var durationResidue: Long = 0

    @SuppressLint("SimpleDateFormat")
    fun getDateAsString(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")

        val calendar = GregorianCalendar(TimeZone.getTimeZone("Europe/Moscow"))
        calendar.timeInMillis = millisec

        return sdf.format(calendar.time)
    }

    override fun equals(other: Any?): Boolean {
        return try{
            val o: ToDoItem = other as ToDoItem
            id==o.id
        }catch (e: Exception){
            false
        }

    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + millisec.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }

}