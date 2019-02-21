package com.practice.work

/**
 * список задач
 */
object ToDoList {
    var data: MutableList<ToDoItem> = mutableListOf()
//    init {
//        data.add(ToDoItem("1", 1234567))
//        data.add(ToDoItem("2", 12345))
//
//    }
private fun getItemById(id: Long): ToDoItem? {
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

    fun add(i:ToDoItem){
        if(!data.contains(i)) {
            data.add(i)
        }
    }

    // return id
    fun newItem(name: String, desc: String, id: Long, time: Long): Long{
        val i = ToDoItem(name,time,id)
        i.description = desc
        add(i)
        return id
    }

    fun commitToDB(db: DBAdapter){
        try {
            data.forEach {
                db.createTask(it.name, it.description, it.millisec)
            }
        }catch (e:Exception){}
    }

//    fun delete(i: ToDoItem){
//        data.remove(i)
//    }
     fun delete(i:Long){
         data.remove(getItemById(i))
     }

//    fun deleteAll(){
//        data = mutableListOf()
//    }
}