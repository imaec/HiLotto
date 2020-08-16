package com.imaec.hilotto

import com.imaec.hilotto.model.LottoDTO
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.ln
import kotlin.math.roundToInt

object Lotto {

    private var minRange = 25
    private var maxRange = 25
    private var count = 6

    private fun getSumMin(list: List<LottoDTO>): Int {
        return getSumAvg(list) - minRange
    }

    private fun getSumMax(list: List<LottoDTO>): Int {
        return getSumAvg(list) + maxRange
    }

    private fun getWeight(list: List<LottoDTO>): HashMap<Int, Int> {
        val w = HashMap<Int, Int>()
        val countSum = ArrayList<Int>().apply {
            (0..44).forEach { _ ->
                add(0)
            }
        }

        list.forEach {
            countSum[it.drwtNo1 - 1] += 1
            countSum[it.drwtNo2 - 1] += 1
            countSum[it.drwtNo3 - 1] += 1
            countSum[it.drwtNo4 - 1] += 1
            countSum[it.drwtNo5 - 1] += 1
            countSum[it.drwtNo6 - 1] += 1
        }

        var bestValue = 0
        countSum.forEachIndexed { i, count ->
            val weightTemp = (if (count == 0) 1.0 else 1.0/count)
            val weight = (weightTemp * 100).roundToInt()
            if (weight in bestValue..99) {
                bestValue = weight
            }
            w[i] = weight
        }
        for (key in w.keys) {
            if (w[key]!! == 100) {
                w[key] = bestValue + 10
            }
        }
        return w
    }

    private fun getWeightedRandom(list: List<LottoDTO>): Int {
        val map = getWeight(list)
        val listNumber = Array(45) { 0 }
        val listWeight = Array(45) { 0 }
        for (key in map.keys) {
            listNumber[key] = key+1
            listWeight[key] = map[key]!!
        }

        var sum = 0
        for (i in listWeight) sum += i
        val r = Random()
        val s = r.nextInt(sum) //Get selection position (not array index)

        //Find position in the array:
        var prevValue = 0
        var currentMaxValue = 0
        var foundIndex = -1
        for (i in listWeight.indices) { //walk through the array
            currentMaxValue = prevValue + listWeight[i]
            //is between beginning and end of this array index?
            val found = s in prevValue until currentMaxValue
            if (found) {
                foundIndex = i
                break
            }
            prevValue = currentMaxValue
        }

        var selection = -1
        if (foundIndex != -1) {
            selection = listNumber[foundIndex]
        }
        return selection
    }

    private fun getRanNumbers(list: List<LottoDTO>): ArrayList<Int> {
        val result = arrayListOf<Int>()
        var isContinue = true
        while (isContinue) {
            var isExist = false
            val number = getWeightedRandom(list)

            result.forEach {
                if (it == number) {
                    isExist = true
                    return@forEach
                }
            }
            if (!isExist) result.add(number)
            if (result.size == count) isContinue = false
        }

        return result
    }

    private fun getSumAvg(list: List<LottoDTO>): Int {
        var sum = 0
        list.map {
            it.drwtNo1 + it.drwtNo2 + it.drwtNo3 + it.drwtNo4 + it.drwtNo5 + it.drwtNo6
        }.forEach {
            sum += it
        }
        return sum / list.size
    }

    private fun getOdd(vararg numbers: Int): List<String> {
        val listOdd = ArrayList<String>()
        numbers.forEach { number ->
            if (number%2 != 0) listOdd.add("$number")
        }
        return listOdd
    }

    private fun getEven(vararg numbers: Int): List<String> {
        val listEven = ArrayList<String>()
        numbers.forEach { number ->
            if (number%2 != 0) listEven.add("$number")
        }
        return listEven
    }

    fun getNumbers(list: List<LottoDTO>, listInclude: List<String>, listNotInclude: List<String>): List<Int> {
        var result = ArrayList<Int>()
        val sumMin = getSumMin(list)
        val sumMax = getSumMax(list)
        var isContinue = true

        while (isContinue) {
            var sum = 0
            var checkNotIncludeNumber = true
            result = getRanNumbers(list)
            listInclude.forEach {
                if (it.isNotEmpty()) result.add(it.toInt())
            }
            // 생성된 번호의 합이 지정된 범위안에 들어오는지 확인
            result.forEach {
                if (listNotInclude.contains("$it")) {
                    checkNotIncludeNumber = false
                    return@forEach
                }
                sum += it
            }
            if (sum in sumMin..sumMax &&
                getOdd(result[0], result[1], result[2], result[3], result[4], result[5]).size in 2..4 &&
                getEven(result[0], result[1], result[2], result[3], result[4], result[5]).size in 2..4 &&
                checkNotIncludeNumber) {
                isContinue = false
            }
        }

        result.sort()
        return result
    }

    fun setMinRange(min: Int) {
        this.minRange = min
    }

    fun setMaxRange(max: Int) {
        this.maxRange = max
    }

    fun setCount(count: Int) {
        this.count = count
    }
}
