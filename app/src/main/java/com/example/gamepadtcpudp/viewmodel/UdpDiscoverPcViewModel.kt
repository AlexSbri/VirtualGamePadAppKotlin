package com.example.gamepadtcpudp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.gamepadtcpudp.tcpudp.UdpDiscoverPc
import com.example.gamepadtcpudp.tcpudp.UdpDiscoverResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UdpDiscoverPcViewModel : ViewModel() {
    private val _discoverResult = MutableStateFlow<UdpDiscoverResult>(UdpDiscoverResult.AwaitingDiscovery)
    val discoverResult: StateFlow<UdpDiscoverResult> = _discoverResult

    fun discoverPc(){
        viewModelScope.launch(Dispatchers.IO) {
            _discoverResult.value = UdpDiscoverResult.AwaitingDiscovery
            _discoverResult.value = UdpDiscoverPc().discoverPc()

            Log.d("Discover Pc","DiscoverResult value ::${_discoverResult.value}")
        }
    }
}