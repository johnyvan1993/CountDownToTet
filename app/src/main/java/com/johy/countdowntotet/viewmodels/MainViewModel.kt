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
    val text: MutableLiveData<String> by lazy { MutableLiveData<String>().apply { value = "" } }

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
        timeParsed.hour = calculate(seconds)
        timeParsed.minute = calculate(seconds - timeParsed.hour * 3600)
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

    private val parseToString: String
        get() {
            val textDate = getApplication<App>().getString(R.string.text_date_time)
            val dayAndTime = dayAndTimeRemain
            val timeParsed = parseTime(dayAndTime.time)
            return textDate.replace("[DAY]", dayAndTime.days.toString())
                .replace(
                    "[HOUR]",
                    timeParsed.hour.toString().let { if (it.length == 1) "0$it" else it })
                .replace(
                    "[MINUTE]",
                    timeParsed.minute.toString().let { if (it.length == 1) "0$it" else it })
                .replace(
                    "[SECOND]",
                    timeParsed.second.toString().let { if (it.length == 1) "0$it" else it })
        }

    val mHandler: Handler? = Handler()
    val mRunnable: Runnable? = object : Runnable {
        override fun run() {
            text.value = parseToString
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