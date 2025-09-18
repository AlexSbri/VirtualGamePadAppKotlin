package com.example.gamepadtcpudp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamepadtcpudp.tcpudp.TcpConnection
import com.example.gamepadtcpudp.tcpudp.UdpDiscoverResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TcpConnectionViewModel : ViewModel() {
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected
    //private val _tcpConnection = MutableLiveData<TcpConnection>()
    //val tcpConnection: LiveData<TcpConnection> = _tcpConnection
    var tcpConnection = TcpConnection()

    fun connectToPc(udpDiscoverPc: UdpDiscoverResult){
        viewModelScope.launch(Dispatchers.IO) {
            tcpConnection.connectPc(udpDiscoverPc)
        }
    }

    fun sendKeyToPc(key : String){
        viewModelScope.launch(Dispatchers.IO) {
            tcpConnection.sendKey(key)
        }
    }

    fun checkConnection(){
        viewModelScope.launch {
            _isConnected.value = async {
                TcpConnection().isSocketAlive()
            }.await()
        }
    }
}