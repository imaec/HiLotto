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

    private val _listStore = MutableLiveData<List<StoreDTO>>().set(ArrayList())
    val listStore: LiveData<List<StoreDTO>> get() = _listStore

    fun setListStore(listStore: List<StoreDTO>) {
        _listStore.value = listStore
    }
}