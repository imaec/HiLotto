package com.imaec.hilotto

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
        val listTemp = arrayListOf("0", "1", "2", "3", "4", "5")
        listTemp.removeAt(0)
        listTemp.add("")

        println(listTemp)
        assertEquals(4, 2 + 2)
    }

    @Test
    fun oddEvenTest() {
        println("${Lotto.getOdd(10, 16, 18, 20, 25, 31)} : ${Lotto.getEven(10, 16, 18, 20, 25, 31)}")
        assert(true)
    }
}
