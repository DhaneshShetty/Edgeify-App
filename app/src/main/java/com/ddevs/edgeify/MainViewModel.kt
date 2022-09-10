package com.ddevs.edgeify

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class MainViewModel:ViewModel() {
    private var _loading:MutableLiveData<Boolean> = MutableLiveData()
    val loading:MutableLiveData<Boolean> get() = _loading
    private var _error:MutableLiveData<String> = MutableLiveData()
    val error:MutableLiveData<String> get() = _error
}