package com.johy.countdowntotet

import com.johy.countdowntotet.defines.Constants
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val current = 1575133200000L
        val betweenLong = Constants.MONG_1_TET - current
        val days = betweenLong / Constants.MILLISECONDS_OF_DAY

        assertEquals(55, days)
    }
}
