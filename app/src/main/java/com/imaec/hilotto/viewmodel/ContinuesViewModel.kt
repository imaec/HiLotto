package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.ContinueDTO
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.ui.adapter.ContinuesAdapter

class ContinuesViewModel : BaseViewModel() {

    init {
        adapter = ContinuesAdapter()
    }

    private val _listContinues = MutableLiveData<List<ContinueDTO>>().set(ArrayList())
    val listContinues: LiveData<List<ContinueDTO>> get() = _listContinues

    private fun getContinues(no1: Int, no2: Int, no3: Int, no4: Int, no5: Int, no6: Int): Array<Int> {
        var sequence2 = 0
        var sequence3 = 0
        var sequence4 = 0
        var sequence5 = 0
        var sequence6 = 0

        // 6연속
        if (no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1 && no2-no1 == 1) {
            sequence6 = 1
        } else {
            // 5연속, 123456
            if (no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1
                || no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1 && no2-no1 == 1) {
                sequence5 = 1
            } else {
                // 4연속
                if (no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1
                    || no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1
                    || no4-no3 == 1 && no3-no2 == 1 && no2-no1 == 1) {
                    sequence4 = 1
                    // 나머지 2개숫자에서 2연속
                    when {
                        // 3456
                        no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1 -> {
                            if (no2-no1 == 1) {
                                sequence2 = 1
                            }
                        }
                        // 1234
                        no4-no3 == 1 && no3-no2 == 1 && no2-no1 == 1 -> {
                            if (no6-no5 == 1) {
                                sequence2 = 1
                            }
                        }
                    }
                } else {
                    // 3연속
                    if (no6-no5 == 1 && no5-no4 == 1
                        || no5-no4 == 1 && no4-no3 == 1
                        || no4-no3 == 1 && no3-no2 == 1
                        || no3-no2 == 1 && no2-no1 == 1) {
                        sequence3 = 1
                        // 나머지 3개숫자에서 3연속
                        when {
                            // 456
                            no6-no5 == 1 && no5-no4 == 1 -> {
                                // 123
                                if (no3-no2 == 1 && no2-no1 == 1) {
                                    // 3연속 + 3연속
                                    sequence3 = 2
                                } else {
                                    // 3연속 + 2연속
                                    if (no3-no2 == 1) {
                                        sequence2 = 1
                                    } else if (no2-no1 == 1) {
                                        sequence2 = 1
                                    }
                                }
                            }
                            // 345
                            no5-no4 == 1 && no4-no3 == 1 -> {
                                // 3연속 + 2연속
                                if (no2-no1 == 1) {
                                    sequence2 = 1
                                }
                            }
                            // 234
                            no4-no3 == 1 && no3-no2 == 1 -> {
                                // 3연속 + 2연속
                                if (no6-no5 == 1) {
                                    sequence2 = 1
                                }
                            }
                            // 123
                            no3-no2 == 1 && no2-no1 == 1 -> {
                                // 3연속 + 2연속
                                if (no5-no4 == 1) {
                                    sequence2 = 1
                                } else if (no6-no5 == 1) {
                                    sequence2 = 1
                                }
                            }
                        }
                    } else {
                        // 2연속
                        if (no6-no5 == 1 || no5-no4 == 1 || no4-no3 == 1 || no3-no2 == 1 || no2-no1 == 1) {
                            sequence2 = 1
                            // 나머지 4개숫자에서 2연속
                            // 2연속 + 2연속
                            when {
                                // 56
                                no6-no5 == 1 -> {
                                    // 2연속 + 2연속 + 2연속
                                    if (no4-no3 == 1 || no3-no2 == 1 || no2-no1 == 1) {
                                        sequence2 = 2
                                        // 나머지 2개숫자에서 2연속
                                        when {
                                            // 34
                                            no4-no3 == 1 -> {
                                                if (no2-no1 == 1) {
                                                    sequence2 = 3
                                                }
                                            }
                                        }
                                    }
                                }
                                // 45
                                no5-no4 == 1 -> {
                                    if (no3-no2 == 1 || no2-no1 == 1) {
                                        sequence2 = 2
                                    }
                                }
                                // 34
                                no4-no3 == 1 -> {
                                    if (no2-no1 == 1) {
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

    private fun getSequenceString(sequences: Array<Int>): String {
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

    private fun getSequenceNumber(no1: Int, no2: Int, no3: Int, no4: Int, no5: Int, no6: Int): Array<ArrayList<Int>> {
        var list1 = ArrayList<Int>()
        var list2 = ArrayList<Int>()
        var list3 = ArrayList<Int>()

        // 6연속
        if (no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1 && no2-no1 == 1) {
            list1 = arrayListOf(no1, no2, no3, no4, no5, no6)
        } else {
            // 5연속, 123456
            if (no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1
                || no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1 && no2-no1 == 1) {
                list1 = if (no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1) {
                    arrayListOf(no2, no3, no4, no5, no6)
                } else {
                    arrayListOf(no1, no2, no3, no4, no5)
                }
            } else {
                // 4연속
                if (no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1
                    || no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1
                    || no4-no3 == 1 && no3-no2 == 1 && no2-no1 == 1) {
                    list1 = if (no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1) {
                        arrayListOf(no3, no4, no5, no6)
                    } else if (no5-no4 == 1 && no4-no3 == 1 && no3-no2 == 1) {
                        arrayListOf(no2, no3, no4, no5)
                    } else {
                        arrayListOf(no1, no2, no3, no4)
                    }
                    // 나머지 2개숫자에서 2연속
                    when {
                        // 3456
                        no6-no5 == 1 && no5-no4 == 1 && no4-no3 == 1 -> {
                            if (no2-no1 == 1) {
                                list2 = arrayListOf(no1, no2)
                            }
                        }
                        // 1234
                        no4-no3 == 1 && no3-no2 == 1 && no2-no1 == 1 -> {
                            if (no6-no5 == 1) {
                                list2 = arrayListOf(no5, no6)
                            }
                        }
                    }
                } else {
                    // 3연속
                    if (no6-no5 == 1 && no5-no4 == 1
                        || no5-no4 == 1 && no4-no3 == 1
                        || no4-no3 == 1 && no3-no2 == 1
                        || no3-no2 == 1 && no2-no1 == 1) {
                        list1 = if (no6-no5 == 1 && no5-no4 == 1) {
                            arrayListOf(no4, no5, no6)
                        } else if (no5-no4 == 1 && no4-no3 == 1) {
                            arrayListOf(no3, no4, no5)
                        } else if (no5-no4 == 1 && no4-no3 == 1) {
                            arrayListOf(no2, no3, no4)
                        } else {
                            arrayListOf(no1, no2, no3)
                        }
                        // 나머지 3개숫자에서 3연속
                        when {
                            // 456
                            no6-no5 == 1 && no5-no4 == 1 -> {
                                // 123
                                if (no3-no2 == 1 && no2-no1 == 1) {
                                    // 3연속 + 3연속
                                    list2 = arrayListOf(no1, no2, no3)
                                } else {
                                    // 3연속 + 2연속
                                    if (no3-no2 == 1) {
                                        list2 = arrayListOf(no2, no3)
                                    } else if (no2-no1 == 1) {
                                        list2 = arrayListOf(no1, no2)
                                    }
                                }
                            }
                            // 345
                            no5-no4 == 1 && no4-no3 == 1 -> {
                                // 3연속 + 2연속
                                if (no2-no1 == 1) {
                                    list2 = arrayListOf(no1, no2)
                                }
                            }
                            // 234
                            no4-no3 == 1 && no3-no2 == 1 -> {
                                // 3연속 + 2연속
                                if (no6-no5 == 1) {
                                    list2 = arrayListOf(no5, no6)
                                }
                            }
                            // 123
                            no3-no2 == 1 && no2-no1 == 1 -> {
                                // 3연속 + 2연속
                                if (no5-no4 == 1) {
                                    list2 = arrayListOf(no4, no5)
                                } else if (no6-no5 == 1) {
                                    list2 = arrayListOf(no5, no6)
                                }
                            }
                        }
                    } else {
                        // 2연속
                        if (no6-no5 == 1 || no5-no4 == 1 || no4-no3 == 1 || no3-no2 == 1 || no2-no1 == 1) {
                            when {
                                no6-no5 == 1 -> list1 = arrayListOf(no5, no6)
                                no5-no4 == 1 -> list1 = arrayListOf(no4, no5)
                                no4-no3 == 1 -> list1 = arrayListOf(no3, no4)
                                no3-no2 == 1 -> list1 = arrayListOf(no2, no3)
                                no2-no1 == 1 -> list1 = arrayListOf(no1, no2)
                            }
                            // 나머지 4개숫자에서 2연속
                            // 2연속 + 2연속
                            when {
                                // 56
                                no6-no5 == 1 -> {
                                    // 2연속 + 2연속 + 2연속
                                    if (no4-no3 == 1 || no3-no2 == 1 || no2-no1 == 1) {
                                        when {
                                            no4-no3 == 1 -> list2 = arrayListOf(no3, no4)
                                            no3-no2 == 1 -> list2 = arrayListOf(no2, no3)
                                            no2-no1 == 1 -> list2 = arrayListOf(no1, no2)
                                        }
                                        // 나머지 2개숫자에서 2연속
                                        when {
                                            // 34
                                            no4-no3 == 1 -> {
                                                if (no2-no1 == 1) {
                                                    list3 = arrayListOf(no1, no2)
                                                }
                                            }
                                        }
                                    }
                                }
                                // 45
                                no5-no4 == 1 -> {
                                    if (no3-no2 == 1 || no2-no1 == 1) {
                                        when {
                                            no3-no2 == 1 -> list2 = arrayListOf(no2, no3)
                                            no2-no1 == 1 -> list2 = arrayListOf(no1, no2)
                                        }
                                    }
                                }
                                // 34
                                no4-no3 == 1 -> {
                                    if (no2-no1 == 1) {
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

    fun setPickedNum(listResult: List<LottoDTO>) {
        val listTemp = ArrayList<ContinueDTO>()
        listResult.subList(listResult.size - 21, listResult.size - 1).reversed().forEach {
            val continues = getContinues(it.drwtNo1, it.drwtNo2, it.drwtNo3, it.drwtNo4, it.drwtNo5, it.drwtNo6)
            val continueString = getSequenceString(continues)
            val continueNum = getSequenceNumber(it.drwtNo1, it.drwtNo2, it.drwtNo3, it.drwtNo4, it.drwtNo5, it.drwtNo6)

            val dto = ContinueDTO(
                "${it.drwNo}회",
                arrayListOf(it.drwtNo1, it.drwtNo2, it.drwtNo3, it.drwtNo4, it.drwtNo5, it.drwtNo6),
                continueString,
                continueNum
            )

            listTemp.add(dto)
        }
        _listContinues.value = listTemp
    }
}