package com.example.gamepadtcpudp.tcpudp

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException

class UdpDiscoverPc {
    private val udpPort = 9876

    internal var tcpPort: Int = 0

    internal var tcpAddress : String = ""


    fun discoverPc(): UdpDiscoverResult{
            try {
                DatagramSocket().use { socket ->
                    socket.broadcast = true

                    //costruzione messaggio e dove inviarlo e a che porta
                    val sendMsg = "DISCOVER_SERVER".toByteArray(Charsets.UTF_8)
                    val broadCastIp = InetAddress.getByName("255.255.255.255")

                    val sendPacket = DatagramPacket(sendMsg, sendMsg.size, broadCastIp, udpPort)
                    socket.send(sendPacket)

                    //3 secondi di ricerca
                    socket.soTimeout = 5000

                    //preparazione buffer per la response
                    val resBuffer = ByteArray(1024)
                    val resPacket = DatagramPacket(resBuffer, resBuffer.size)
                    try {
                        socket.receive(resPacket)
                        val response = String(resPacket.data, 0, resPacket.length, Charsets.UTF_8)

                        Log.d("DISCOVER PC UDP", " ${resPacket.address}\nResponse: $response")
                        tcpAddress = resPacket.address.hostAddress
                        tcpPort = response.toInt()
                        Log.d("DISCOVER PC UDP ", "tcp address::$tcpAddress\ntcp post ::$tcpPort")
                        return UdpDiscoverResult.Found(tcpAddress,tcpPort)
                    } catch (e: SocketTimeoutException) {
                        Log.d("DISCOVER PC UDP", "non trovato niente $e")
                    }
                }
            } catch (ex: Exception) {
                Log.d("DISCOVER PC UDP", "Error MSG $ex")
            }
        return UdpDiscoverResult.NotFound
    }
}

sealed class UdpDiscoverResult{
    data class Found(val tcpAddress: String , val port :Int) : UdpDiscoverResult()
    object NotFound : UdpDiscoverResult()
    object AwaitingDiscovery : UdpDiscoverResult()
}