package com.imaec.hilotto

// Lotto Parsing Url
const val URL_LOTTO = "https://www.dhlottery.co.kr/common.do?method=main&mainMode=default"
const val URL_STORE = "https://www.dhlottery.co.kr/store.do?method=topStore&pageGubun=L645"

// Intent Extra Key
const val EXTRA_NUMBER_ID = "extra_number_id"
const val EXTRA_NUMBER_1 = "extra_number_1"
const val EXTRA_NUMBER_2 = "extra_number_2"
const val EXTRA_NUMBER_3 = "extra_number_3"
const val EXTRA_NUMBER_4 = "extra_number_4"
const val EXTRA_NUMBER_5 = "extra_number_5"
const val EXTRA_NUMBER_6 = "extra_number_6"
const val EXTRA_LIST_LOTTO = "extra_list_lotto"
const val EXTRA_LATELY_RESULT_POSITION = "extra_lately_result_position"
const val EXTRA_CURRENT_ROUND = "extra_current_round"
const val EXTRA_LIST_STORE = "extra_list_store"
const val EXTRA_MY_NUMBER = "extra_my_number"

// Request Code
const val REQUEST_EDIT_NUMBER = 10
const val REQUEST_CREATE_FILE = 20
const val REQUEST_PERMISSION_EXPORT = 100
const val REQUEST_PERMISSION_IMPORT = 200

class Event {
    companion object {
        const val CREATE_NUMBER = "create_number"
        const val SAVE_NUMBER = "save_number"
    }
}