package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.ui.adapter.StoreAdapter

class StoreViewModel : BaseViewModel() {

    init {
        adapter = StoreAdapter()
    }

    private val _listStore = MutableLiveData<List<StoreDTO>>(ArrayList())
    val listStore: LiveData<List<StoreDTO>> get() = _listStore

    private val _round = MutableLiveData<String>("0회")
    val round: LiveData<String> get() = _round

    fun setListStore(listStore: List<StoreDTO>) {
        _listStore.value = listStore
    }

    fun setRound(round: Int) {
        _round.value = "${round}회"
    }

    fun setOnItemClickListener(onClick: (Any) -> Unit) {
        adapter.addOnClickListener { onClick(it) }
    }
}