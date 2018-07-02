package com.ayush171196.Startup

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import java.util.*

class SaveData{

    var context:Context?=null
    var sharedRef:SharedPreferences?=null
    constructor(context: Context){
        this.context=context
        this.sharedRef=context.getSharedPreferences("myRef",Context.MODE_PRIVATE)
    }

    fun SaveData(hour: Int,minute: Int){
        var editor = sharedRef!!.edit()
        editor.putInt("hour",hour)
        editor.putInt("minute",minute)
        editor.commit()
    }

    fun getHour():Int{
        return sharedRef!!.getInt("hour",0)
    }

    fun getMinute():Int{
        return sharedRef!!.getInt("minute",0)
    }

    fun  setAlarm(){
        val hour:Int = getHour()
        val minute:Int = getMinute()
        val calendar= Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,hour)
        calendar.set(Calendar.MINUTE,minute)
        calendar.set(Calendar.SECOND,0)

        val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var intent = Intent(context,myBroadcastReciever::class.java)
        intent.putExtra("message","alarm time")
        intent.action="com.tester.alarmmanager"
        val pi = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,pi)
    }
}