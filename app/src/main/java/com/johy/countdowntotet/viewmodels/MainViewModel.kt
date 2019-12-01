package com.johy.countdowntotet.viewmodels

import android.app.Application
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.johy.countdowntotet.R
import com.johy.countdowntotet.app.App
import com.johy.countdowntotet.base.BaseViewModel
import com.johy.countdowntotet.defines.Constants
import java.util.*

open class MainViewModel(app: Application) : BaseViewModel(app) {
    val day: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val hour: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val minute: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }
    val second: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }

    private val remainTime: Long
        get() {
            return Constants.MONG_1_TET - Date().time
        }
    private val dayAndTimeRemain: DayAndTimeRemain
        get() {
            val days: Int = (remainTime / Constants.MILLISECONDS_OF_DAY).toInt()
            val timeRemain = remainTime - days * Constants.MILLISECONDS_OF_DAY
            return DayAndTimeRemain(days, timeRemain)
        }

    private fun parseTime(milliseconds: Long): TimeParsed {
        val seconds = milliseconds / 1000
        val timeParsed = TimeParsed(0, 0, 0)
        timeParsed.hour = calculate(if (seconds >= 3600) seconds else 0)
        timeParsed.minute = calculate(if (seconds >= 60) seconds - timeParsed.hour * 3600 else 0)
        timeParsed.second = calculate(seconds - timeParsed.hour * 3600 - timeParsed.minute * 60)
        return timeParsed
    }

    private fun calculate(seconds: Long): Long {
        return when {
            seconds >= 3600 -> {
                seconds / 3600
            }
            seconds >= 60 -> {
                seconds / 60
            }
            else -> {
                seconds
            }
        }
    }

    fun parse() {
        val dayAndTime = dayAndTimeRemain
        val timeParsed = parseTime(dayAndTime.time)
        day.value = dayAndTime.days.toString()
        hour.value = timeParsed.hour.toString().let { if (it.length == 1) "0$it" else it }
        minute.value = timeParsed.minute.toString().let { if (it.length == 1) "0$it" else it }
        second.value = timeParsed.second.toString().let { if (it.length == 1) "0$it" else it }
    }

    val mHandler: Handler? = Handler()
    val mRunnable: Runnable? = object : Runnable {
        override fun run() {
            parse()
            mHandler?.postDelayed(this, Constants.ONE_SECOND)
        }
    }

    data class DayAndTimeRemain(val days: Int, val time: Long)
    data class TimeParsed(
        var hour: Long,
        var minute: Long,
        var second: Long
    )
}