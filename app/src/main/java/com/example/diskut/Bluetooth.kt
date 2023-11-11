package com.example.diskut

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.UUID

val warrenMAC = "30:74:67:52:97:D8"
val uuid = UUID.fromString("317a9c6e-90d1-418b-afa4-b5866a52bb5c")

const val SERVER = 0
const val CLIENT = 1

class Bluetooth(val adapter: BluetoothAdapter) {

    private var mode: Int? = null
    private lateinit var server: Server
    private lateinit var client: Client

    private inner class Server(val runOnAccept: (BluetoothSocket) -> Unit) : Thread() {

        private val socket: BluetoothServerSocket? by lazy (LazyThreadSafetyMode.NONE) {
            adapter.listenUsingRfcommWithServiceRecord("diskut", uuid)
        }

        lateinit var bluetoothSocket: BluetoothSocket

        override fun run() {
            // Keep listening until exception occurs or a socket is returned.
            var shouldLoop = true
            while (shouldLoop) {
                val socket: BluetoothSocket? = try {
                    socket?.accept()
                } catch (e: IOException) {
                    Log.e("diskut", "Socket's accept() method failed", e)
                    shouldLoop = false
                    null
                }
                socket?.also {
                    bluetoothSocket = it
                    runOnAccept(it)
//                    socket.close()
                    shouldLoop = false
                }
            }
        }

        // Closes the connect socket and causes the thread to finish.
        fun cancel() {
            try {
                Log.i("diskut", bluetoothSocket.toString())
                bluetoothSocket.close()
            } catch (e: IOException) {
                Log.e("diskut", "Could not close the connect socket", e)
            }
        }

        fun receive(bytes: ByteArray) {
            bluetoothSocket.inputStream.read(bytes)
        }

        fun send(bytes: ByteArray) {
            try {
                bluetoothSocket.outputStream.write(bytes)
            } catch (e: IOException) {
                Log.e("duskit", "Error occurred when sending data", e)
            }
        }
    }

    private inner class Client(val runOnConnect: (BluetoothSocket) -> Unit) : Thread() {
        val socket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            adapter
                .getRemoteDevice(warrenMAC)
                .createRfcommSocketToServiceRecord(uuid)
        }

        override fun run() {
            Log.i("diskut", "run")
            // Cancel discovery because it otherwise slows down the connection.
            adapter.cancelDiscovery()

            socket?.let { socket ->
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                socket.connect()

                // The connection attempt succeeded. Perform work associated with
                // the connection in a separate thread.
                runOnConnect(socket)
            }
        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
            try {
                Log.i("diskut", socket.toString())
                socket?.close()
            } catch (e: IOException) {
                Log.e("diskut", "Could not close the client socket", e)
            }
        }

        fun receive(bytes: ByteArray) {
            Log.i("diskut", socket!!.inputStream.available().toString())
            socket!!.inputStream.read(bytes)
        }

        fun send(bytes: ByteArray) {
            try {
                socket?.outputStream?.write(bytes)
            } catch (e: IOException) {
                Log.e("duskit", "Error occurred when sending data", e)
            }
        }
    }

    fun startServer(runOnAccept: (BluetoothSocket) -> Unit) {
        if (mode == CLIENT) return
        stop()
        mode = SERVER
        server = Server(runOnAccept).also {
            it.start()
        }
    }

    fun startClient(runOnConnect: (BluetoothSocket) -> Unit) {
        if (mode == SERVER) return
        stop()
        mode = CLIENT
        client = Client(runOnConnect).also {
            it.start()
        }
    }


    fun stop(onStop: () -> Unit = {}) {
        when (mode) {
            CLIENT -> client.cancel()
            SERVER -> server.cancel()
        }
        onStop()
        mode = null
    }

    fun send(bytes: ByteArray) {
        when (mode) {
            CLIENT -> client.send(bytes)
            SERVER -> server.send(bytes)
        }
    }

    fun receive(bytes: ByteArray) {
        when (mode) {
            CLIENT -> client.receive(bytes)
            SERVER -> server.receive(bytes)
        }
        Log.i("diskut", bytes.decodeToString().trim { it < ' ' })
    }

    fun active(): Boolean {
        return when (mode) {
            CLIENT -> client.socket!!.isConnected
            SERVER -> server.bluetoothSocket.isConnected
            else -> false
        }
    }

//    fun alertAndClose() {
//        val STOP = byteArrayOf('\u1337'.code.toByte())
//        when (mode) {
//            CLIENT -> client.send(STOP)
//            SERVER -> server.send(STOP)
//        }
//        stop()
//    }
}