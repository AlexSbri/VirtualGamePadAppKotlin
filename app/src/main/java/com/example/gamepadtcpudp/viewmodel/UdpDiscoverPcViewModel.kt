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
    private val _discoverResult = MutableStateFlow<UdpDiscoverResult>(UdpDiscoverResult.NotFound)
    val discoverResult: StateFlow<UdpDiscoverResult> = _discoverResult


    var discoverData : UdpDiscoverResult = UdpDiscoverResult.NotFound

    fun DiscoverPc(){
        viewModelScope.launch(Dispatchers.IO) {
            _discoverResult.value = UdpDiscoverPc().discoverPc()
            when(_discoverResult.value){
                is UdpDiscoverResult.Found -> Log.d("VIEWMODEL UDP", "TCP ADDRESSS ::: ${discoverData}")
                UdpDiscoverResult.NotFound -> Log.d("VIEWMODEL UDP", "TCP ADDRESSS ::: NOT FOUND")
                UdpDiscoverResult.AwaitingDiscovery -> TODO()
            }
            Log.d("VIEWMODEL UDP", "TCP ADDRESSS ::: ${discoverData}")
        }
    }
}