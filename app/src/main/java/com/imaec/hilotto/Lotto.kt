package com.imaec.hilotto

import com.imaec.hilotto.model.LottoDTO
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.ln
import kotlin.math.roundToInt

object Lotto {

    fun getSumAvg(list: ArrayList<LottoDTO>): Int {
        var sum = 0
        list.map {
            it.drwtNo1 + it.drwtNo2 + it.drwtNo3 + it.drwtNo4 + it.drwtNo5 + it.drwtNo6
        }.forEach {
            sum += it
        }
        return sum / list.size
    }

    fun getSumMin(list: ArrayList<LottoDTO>): Int {
        return getSumAvg(list) - 25
    }

    fun getSumMax(list: ArrayList<LottoDTO>): Int {
        return getSumAvg(list) + 25
    }

    fun getNumbers(list: ArrayList<LottoDTO>): ArrayList<Int> {
        var result = ArrayList<Int>()
        val sumMin = getSumMin(list)
        val sumMax = getSumMax(list)
        var isContinue = true

        while (isContinue) {
            var sum = 0
            result = getRanNumbers(list)
            result.forEach {
                sum += it
            }
//            println(sum)

            result.sort()
//            println(result)
            if (sum in sumMin..sumMax) {
                isContinue = false
            }
        }

        return result
    }

    private fun getRanNumbers(list: ArrayList<LottoDTO>): ArrayList<Int> {
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
            if (result.size == 6) isContinue = false
        }

        return result
    }

    private fun getWeightedRandom(list: ArrayList<LottoDTO>): Int {
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

    fun getWeight(list: ArrayList<LottoDTO>): HashMap<Int, Int> {
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
//            weight = (weight * 100).roundToInt() / 100.0
            val weight = (weightTemp * 100).roundToInt()
            if (weight in bestValue..99) {
                bestValue = weight
            }
            w[i] = weight
            // println("${i+1} : $count / $weight")
        }
        for (key in w.keys) {
            if (w[key]!! == 100) {
                w[key] = bestValue + 10
            }
        }
        return w
    }
}