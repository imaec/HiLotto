package com.imaec.hilotto

import com.imaec.hilotto.model.LottoDTO
import java.util.Random
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.roundToInt

object Lotto {

    private val TAG = this::class.java.simpleName

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
//        if (!condPick) {
//            (0..5).forEach {
//                w[it] = 1
//            }
//            return w
//        }
        val countSum = ArrayList<Int>().apply {
            (0..44).forEach { _ ->
                add(0)
            }
        }

        list.forEach {
            if (it.drwNo == 0) return@forEach
            countSum[it.drwtNo1 - 1] += 1
            countSum[it.drwtNo2 - 1] += 1
            countSum[it.drwtNo3 - 1] += 1
            countSum[it.drwtNo4 - 1] += 1
            countSum[it.drwtNo5 - 1] += 1
            countSum[it.drwtNo6 - 1] += 1
        }

        var bestValue = 0
        countSum.forEachIndexed { i, count ->
            val weightTemp = (if (count == 0) 1.0 else 1.0 / count)
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
            listNumber[key] = key + 1
            listWeight[key] = map[key]!!
        }

        var sum = 0
        for (i in listWeight) sum += i
        val r = Random()
        val s = r.nextInt(sum) // Get selection position (not array index)

        // Find position in the array:
        var prevValue = 0
        var currentMaxValue = 0
        var foundIndex = -1
        for (i in listWeight.indices) { // walk through the array
            currentMaxValue = prevValue + listWeight[i]
            // is between beginning and end of this array index?
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

    private fun getRandom(): Int {
        return Random().nextInt(45) + 1
    }

    private fun getRanNumbers(list: List<LottoDTO>, condPick: Boolean): ArrayList<Int> {
        val result = arrayListOf<Int>()
        var isContinue = true
        while (isContinue) {
            var isExist = false
            val number = if (condPick) getWeightedRandom(list) else getRandom()

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

    fun getSumAvg(list: List<LottoDTO>): Int {
        var sum = 0
        list.map {
            it.drwtNo1 + it.drwtNo2 + it.drwtNo3 + it.drwtNo4 + it.drwtNo5 + it.drwtNo6
        }.forEach {
            sum += it
        }
        return sum / list.size
    }

    fun getOdd(numbers: List<Int>): List<String> {
        val listOdd = ArrayList<String>()
        numbers.forEach { number ->
            if (number % 2 != 0) listOdd.add("$number")
        }
        return listOdd
    }

    fun getEven(numbers: List<Int>): List<String> {
        val listEven = ArrayList<String>()
        numbers.forEach { number ->
            if (number % 2 == 0) listEven.add("$number")
        }
        return listEven
    }

    fun getNumbers(
        list: List<LottoDTO>,
        listInclude: List<String>,
        listNotInclude: List<String>,
        condSum: Boolean = true,
        condPick: Boolean = true,
        condOddEven: Boolean = true
    ): List<Int> {
        var result = ArrayList<Int>()
        val sumMin = getSumMin(list)
        val sumMax = getSumMax(list)
        var isContinue = true

        while (isContinue) {
            var sum = 0
            var checkNotIncludeNumber = true
            result = getRanNumbers(list, condPick)
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
            val isSum = if (condSum) sum in sumMin..sumMax else true
            val isOddEven = if (condOddEven) {
                getOdd(result).size in 2..4 && getEven(result).size in 2..4
            } else true

            if (isSum && isOddEven && checkNotIncludeNumber) {
                isContinue = false
            }
        }

        result.sort()
        return result
    }

    fun getContinues(no1: Int, no2: Int, no3: Int, no4: Int, no5: Int, no6: Int): Array<Int> {
        var sequence2 = 0
        var sequence3 = 0
        var sequence4 = 0
        var sequence5 = 0
        var sequence6 = 0

        // 6연속
        if (no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1 &&
            no3 - no2 == 1 && no2 - no1 == 1
        ) {
            sequence6 = 1
        } else {
            // 5연속, 123456
            if (
                no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1 && no3 - no2 == 1 ||
                no5 - no4 == 1 && no4 - no3 == 1 && no3 - no2 == 1 && no2 - no1 == 1
            ) {
                sequence5 = 1
            } else {
                // 4연속
                if (
                    no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1 ||
                    no5 - no4 == 1 && no4 - no3 == 1 && no3 - no2 == 1 ||
                    no4 - no3 == 1 && no3 - no2 == 1 && no2 - no1 == 1
                ) {
                    sequence4 = 1
                    // 나머지 2개숫자에서 2연속
                    when {
                        // 3456
                        no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1 -> {
                            if (no2 - no1 == 1) {
                                sequence2 = 1
                            }
                        }
                        // 1234
                        no4 - no3 == 1 && no3 - no2 == 1 && no2 - no1 == 1 -> {
                            if (no6 - no5 == 1) {
                                sequence2 = 1
                            }
                        }
                    }
                } else {
                    // 3연속
                    if (
                        no6 - no5 == 1 && no5 - no4 == 1 ||
                        no5 - no4 == 1 && no4 - no3 == 1 ||
                        no4 - no3 == 1 && no3 - no2 == 1 ||
                        no3 - no2 == 1 && no2 - no1 == 1
                    ) {
                        sequence3 = 1
                        // 나머지 3개숫자에서 3연속
                        when {
                            // 456
                            no6 - no5 == 1 && no5 - no4 == 1 -> {
                                // 123
                                if (no3 - no2 == 1 && no2 - no1 == 1) {
                                    // 3연속 + 3연속
                                    sequence3 = 2
                                } else {
                                    // 3연속 + 2연속
                                    if (no3 - no2 == 1) {
                                        sequence2 = 1
                                    } else if (no2 - no1 == 1) {
                                        sequence2 = 1
                                    }
                                }
                            }
                            // 345
                            no5 - no4 == 1 && no4 - no3 == 1 -> {
                                // 3연속 + 2연속
                                if (no2 - no1 == 1) {
                                    sequence2 = 1
                                }
                            }
                            // 234
                            no4 - no3 == 1 && no3 - no2 == 1 -> {
                                // 3연속 + 2연속
                                if (no6 - no5 == 1) {
                                    sequence2 = 1
                                }
                            }
                            // 123
                            no3 - no2 == 1 && no2 - no1 == 1 -> {
                                // 3연속 + 2연속
                                if (no5 - no4 == 1) {
                                    sequence2 = 1
                                } else if (no6 - no5 == 1) {
                                    sequence2 = 1
                                }
                            }
                        }
                    } else {
                        // 2연속
                        if (no6 - no5 == 1 || no5 - no4 == 1 || no4 - no3 == 1 ||
                            no3 - no2 == 1 || no2 - no1 == 1
                        ) {
                            sequence2 = 1
                            // 나머지 4개숫자에서 2연속
                            // 2연속 + 2연속
                            when {
                                // 56
                                no6 - no5 == 1 -> {
                                    // 2연속 + 2연속 + 2연속
                                    if (no4 - no3 == 1 || no3 - no2 == 1 || no2 - no1 == 1) {
                                        sequence2 = 2
                                        // 나머지 2개숫자에서 2연속
                                        when {
                                            // 34
                                            no4 - no3 == 1 -> {
                                                if (no2 - no1 == 1) {
                                                    sequence2 = 3
                                                }
                                            }
                                        }
                                    }
                                }
                                // 45
                                no5 - no4 == 1 -> {
                                    if (no3 - no2 == 1 || no2 - no1 == 1) {
                                        sequence2 = 2
                                    }
                                }
                                // 34
                                no4 - no3 == 1 -> {
                                    if (no2 - no1 == 1) {
                                        sequence2 = 2
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return arrayOf(sequence2, sequence3, sequence4, sequence5, sequence6)
    }

    fun getSequenceString(sequences: Array<Int>): String {
        var sequenceString: String

        when {
            // 6연속
            sequences[4] > 0 -> {
                sequenceString = "6연속 ${sequences[4]}개"
            }
            // 5연속
            sequences[3] > 0 -> {
                sequenceString = "5연속 ${sequences[3]}개"
            }
            // 4연속
            sequences[2] > 0 -> {
                sequenceString = "4연속 ${sequences[2]}개"
                // 2연속
                if (sequences[0] > 0) {
                    sequenceString += "\n2연속 ${sequences[0]}개"
                }
            }
            // 3연속
            sequences[1] > 0 -> {
                sequenceString = "3연속 ${sequences[1]}개"
                // 2연속
                if (sequences[0] > 0) {
                    sequenceString += "\n2연속 ${sequences[0]}개"
                }
            }
            // 2연속
            sequences[0] > 0 -> {
                sequenceString = "2연속 ${sequences[0]}개"
            }
            else -> {
                sequenceString = "연속번호 없음"
            }
        }

        return sequenceString
    }

    fun getSequenceNumber(
        no1: Int,
        no2: Int,
        no3: Int,
        no4: Int,
        no5: Int,
        no6: Int
    ): Array<ArrayList<Int>> {
        var list1 = ArrayList<Int>()
        var list2 = ArrayList<Int>()
        var list3 = ArrayList<Int>()

        // 6연속
        if (no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1 &&
            no3 - no2 == 1 && no2 - no1 == 1
        ) {
            list1 = arrayListOf(no1, no2, no3, no4, no5, no6)
        } else {
            // 5연속, 123456
            if (
                no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1 && no3 - no2 == 1 ||
                no5 - no4 == 1 && no4 - no3 == 1 && no3 - no2 == 1 && no2 - no1 == 1
            ) {
                list1 = if (no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1 && no3 - no2 == 1) {
                    arrayListOf(no2, no3, no4, no5, no6)
                } else {
                    arrayListOf(no1, no2, no3, no4, no5)
                }
            } else {
                // 4연속
                if (
                    no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1 ||
                    no5 - no4 == 1 && no4 - no3 == 1 && no3 - no2 == 1 ||
                    no4 - no3 == 1 && no3 - no2 == 1 && no2 - no1 == 1
                ) {
                    list1 = if (no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1) {
                        arrayListOf(no3, no4, no5, no6)
                    } else if (no5 - no4 == 1 && no4 - no3 == 1 && no3 - no2 == 1) {
                        arrayListOf(no2, no3, no4, no5)
                    } else {
                        arrayListOf(no1, no2, no3, no4)
                    }
                    // 나머지 2개숫자에서 2연속
                    when {
                        // 3456
                        no6 - no5 == 1 && no5 - no4 == 1 && no4 - no3 == 1 -> {
                            if (no2 - no1 == 1) {
                                list2 = arrayListOf(no1, no2)
                            }
                        }
                        // 1234
                        no4 - no3 == 1 && no3 - no2 == 1 && no2 - no1 == 1 -> {
                            if (no6 - no5 == 1) {
                                list2 = arrayListOf(no5, no6)
                            }
                        }
                    }
                } else {
                    // 3연속
                    if (
                        no6 - no5 == 1 && no5 - no4 == 1 ||
                        no5 - no4 == 1 && no4 - no3 == 1 ||
                        no4 - no3 == 1 && no3 - no2 == 1 ||
                        no3 - no2 == 1 && no2 - no1 == 1
                    ) {
                        list1 = if (no6 - no5 == 1 && no5 - no4 == 1) {
                            arrayListOf(no4, no5, no6)
                        } else if (no5 - no4 == 1 && no4 - no3 == 1) {
                            arrayListOf(no3, no4, no5)
                        } else if (no5 - no4 == 1 && no4 - no3 == 1) {
                            arrayListOf(no2, no3, no4)
                        } else {
                            arrayListOf(no1, no2, no3)
                        }
                        // 나머지 3개숫자에서 3연속
                        when {
                            // 456
                            no6 - no5 == 1 && no5 - no4 == 1 -> {
                                // 123
                                if (no3 - no2 == 1 && no2 - no1 == 1) {
                                    // 3연속 + 3연속
                                    list2 = arrayListOf(no1, no2, no3)
                                } else {
                                    // 3연속 + 2연속
                                    if (no3 - no2 == 1) {
                                        list2 = arrayListOf(no2, no3)
                                    } else if (no2 - no1 == 1) {
                                        list2 = arrayListOf(no1, no2)
                                    }
                                }
                            }
                            // 345
                            no5 - no4 == 1 && no4 - no3 == 1 -> {
                                // 3연속 + 2연속
                                if (no2 - no1 == 1) {
                                    list2 = arrayListOf(no1, no2)
                                }
                            }
                            // 234
                            no4 - no3 == 1 && no3 - no2 == 1 -> {
                                // 3연속 + 2연속
                                if (no6 - no5 == 1) {
                                    list2 = arrayListOf(no5, no6)
                                }
                            }
                            // 123
                            no3 - no2 == 1 && no2 - no1 == 1 -> {
                                // 3연속 + 2연속
                                if (no5 - no4 == 1) {
                                    list2 = arrayListOf(no4, no5)
                                } else if (no6 - no5 == 1) {
                                    list2 = arrayListOf(no5, no6)
                                }
                            }
                        }
                    } else {
                        // 2연속
                        if (no6 - no5 == 1 || no5 - no4 == 1 || no4 - no3 == 1 ||
                            no3 - no2 == 1 || no2 - no1 == 1
                        ) {
                            when {
                                no6 - no5 == 1 -> list1 = arrayListOf(no5, no6)
                                no5 - no4 == 1 -> list1 = arrayListOf(no4, no5)
                                no4 - no3 == 1 -> list1 = arrayListOf(no3, no4)
                                no3 - no2 == 1 -> list1 = arrayListOf(no2, no3)
                                no2 - no1 == 1 -> list1 = arrayListOf(no1, no2)
                            }
                            // 나머지 4개숫자에서 2연속
                            // 2연속 + 2연속
                            when {
                                // 56
                                no6 - no5 == 1 -> {
                                    // 2연속 + 2연속 + 2연속
                                    if (no4 - no3 == 1 || no3 - no2 == 1 || no2 - no1 == 1) {
                                        when {
                                            no4 - no3 == 1 -> list2 = arrayListOf(no3, no4)
                                            no3 - no2 == 1 -> list2 = arrayListOf(no2, no3)
                                            no2 - no1 == 1 -> list2 = arrayListOf(no1, no2)
                                        }
                                        // 나머지 2개숫자에서 2연속
                                        when {
                                            // 34
                                            no4 - no3 == 1 -> {
                                                if (no2 - no1 == 1) {
                                                    list3 = arrayListOf(no1, no2)
                                                }
                                            }
                                        }
                                    }
                                }
                                // 45
                                no5 - no4 == 1 -> {
                                    if (no3 - no2 == 1 || no2 - no1 == 1) {
                                        when {
                                            no3 - no2 == 1 -> list2 = arrayListOf(no2, no3)
                                            no2 - no1 == 1 -> list2 = arrayListOf(no1, no2)
                                        }
                                    }
                                }
                                // 34
                                no4 - no3 == 1 -> {
                                    if (no2 - no1 == 1) {
                                        list2 = arrayListOf(no1, no2)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return arrayOf(list1, list2, list3)
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
