package com.practice.work

object PlanList {
     
    val MIN_DURATION: Long = 10
    val MAX_DURATION: Long = 30

    

    var data: MutableList<PlanItem> = mutableListOf()

    fun getItemById(id: Long): PlanItem? {
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

    fun add(i:PlanItem){
        if(!data.contains(i)) {
            data.add(i)
        }
    }

    // return id
    fun newItem(name: String, desc: String = "", timeStart: Long, timeEnd: Long): Long{
        val i = PlanItem(name,timeStart,timeEnd)
        i.description = desc
        i.id = (Math.random()*1000).toLong()
        add(i)
        return i.id
    }

    fun commitToDB(db: DBAdapter){
        try {
            data.forEach {
                db.createPlan(it)
            }
        }catch (e:Exception){}
    }

    fun delete(i: PlanItem){
        data.remove(i)
    }
    fun delete(i:Long){
        data.remove(getItemById(i))
    }

    fun deleteAll(){
        data = mutableListOf()
    }
}