package com.practice.work


/**
 * Пункт плана
 */
class PlanItem(var name: String): Comparable<PlanItem>{

    constructor(name: String, timeStart: Long, timeEnd: Long) : this(name) {
        this.timeStart = timeStart
        this.timeEnd = timeEnd
    }
    constructor(name: String, timeStart: Long, timeEnd: Long, task: ToDoItem):this(name){
        this.timeStart = timeStart
        this.timeEnd = timeEnd
        this.task = task
    }

    var timeStart:Long = 0
    var timeEnd:Long = 0
    lateinit var description: String
    var id: Long = (Math.random()*1000).toLong()
    var duration = timeEnd - timeStart
    var task: ToDoItem? = null

    override fun compareTo(other: PlanItem): Int {
        return timeStart.compareTo(other.timeStart)
    }

    override fun equals(other: Any?): Boolean = try{
        val o: PlanItem = other as PlanItem
        id==o.id
    }catch (e: Exception){
        false
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + timeStart.hashCode()
        result = 31 * result + timeEnd.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }


}