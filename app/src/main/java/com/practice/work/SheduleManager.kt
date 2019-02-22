package com.practice.work

object SheduleManager {
    private const val startDay: Long = 0

    fun getFreeTime(): PlanItem?{
        var endPreviousPlan = startDay
        var planItem: PlanItem? = null
        for(plan in PlanList.data) {
            var startPlan = plan.timeStart
            if (startPlan - endPreviousPlan > PlanList.MIN_DURATION)
                planItem = PlanItem("Free", endPreviousPlan, startPlan)
            endPreviousPlan = plan.timeEnd
        }
        return planItem
    }

    fun isFree(time: Long): Boolean{
        var endPreviousPlan = startDay
        for(plan in PlanList.data){
            if (plan.timeStart<=time&&time<plan.timeEnd)
                return false
            if((plan.timeStart>time&&time>=endPreviousPlan)&&(plan.timeStart-time>=PlanList.MIN_DURATION))
                return true
            endPreviousPlan = plan.timeEnd
        }
        return true
    }
//    getFreeTimes()
//    PlanItem
//
//    time(t)
}