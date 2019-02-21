package com.practice.work

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
    var date  = Date(millisec)
    var duration = 1111
   // var priority = 0
//    var progress = 0
    var durationResidue: Long = 0

//    fun setPlan(plan: PlanItem): Boolean{
//        if (durationResidue<=plan.duration) {
//            plans.add(plan)
//            return true
//        }
//        else if (durationResidue - plan.duration>= PlanList.MIN_DURATION) {
//            durationResidue -= plan.duration
//            return true
//        }
//        return false
//    }


    fun getDateAsString(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")

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

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + millisec.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }

}