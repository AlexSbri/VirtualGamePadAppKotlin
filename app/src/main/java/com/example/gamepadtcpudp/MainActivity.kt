package com.example.gamepadtcpudp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamepadtcpudp.ui.screen.ConnectionScreenView
import com.example.gamepadtcpudp.ui.screen.GamePadScreen
import com.example.gamepadtcpudp.ui.theme.GamePadTCPUDPTheme
import com.example.gamepadtcpudp.viewmodel.TcpConnectionViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI(this)
        setContent {
            GamePadTCPUDPTheme(dynamicColor = false) {

                val tcpConnectionviewModel: TcpConnectionViewModel = viewModel()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "ConnessionePc") {
                    composable("ConnessionePc") { ConnectionScreenView(navController,tcpConnectionviewModel) }
                    composable("GamePad") { GamePadScreen(tcpConnectionviewModel) }
                }
            }
        }
    }
    fun hideSystemUI(activity: Activity) {
        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
    }

}



