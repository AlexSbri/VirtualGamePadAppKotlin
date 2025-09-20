package com.example.gamepadtcpudp.tcpudp

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class TcpConnection {
    var socket: Socket ?= null

    fun connectPc(udpDiscover: UdpDiscoverResult): Boolean {
        return when(udpDiscover){
            is UdpDiscoverResult.Found -> {
                val address = udpDiscover.tcpAddress
                val port = udpDiscover.port
                Log.d("Connect to Pc","Trovato : $address:$port")

                try {
                    socket = Socket(address, port)
                    true
                } catch (e : Exception){
                    Log.d("TcpConnection", "errore nella connessione:: $e")
                    false
                }
            }
            else -> false
        }
    }


    fun sendKey(key: String) {
        try {
            socket?.getOutputStream()?.let { stream ->
                socket?.tcpNoDelay = true
                stream.write(key.toByteArray())
                stream.flush() // Assicura che il comando venga inviato subito
                //socket?.close()
            } ?: Log.d("SENDKEY OUTPUTSTREAM","Socket o getoutput stream sono null")
        } catch (e: IOException) {
            Log.e("TcpConnection", "Errore nell'invio del comando: $e")
        }
    }

    fun closeSocket(): Boolean{
        return try {
            socket?.close()
            socket = null
            return false
        }catch (e: IOException) {
            Log.d("CloseSocket", "exception chiiusura: $e")
            false
        }
    }

    fun isSocketAlive(): Boolean {
        return try {
            socket?.getOutputStream()?.write(0) // Provo a inviare un byte "dummy"
            socket?.getOutputStream()?.flush()
            true // Se arriva qui, il socket è attivo!
        } catch (e: IOException) {
            false // Se c'è errore, la connessione non è più attiva
        }
    }

}

