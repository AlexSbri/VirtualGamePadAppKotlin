package com.example.gamepadtcpudp.ui.components

import android.text.Layout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamepadtcpudp.ui.theme.GamePadTCPUDPTheme


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    internal fun TopBarGamePad(title: String){
        var expanded by remember { mutableStateOf(false) }
        MediumTopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            title = {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
            },
            actions = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Impostazioni",
                        tint = MaterialTheme.colorScheme.onSurface
                        )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false},
                ) {
                    DropdownMenuItem(text = {Text("Modifica Tasti")}, onClick = {/*Aggiungere azione */})
                    DropdownMenuItem(text = {Text("Chiudi connessione")}, onClick = {/*Aggiungere chiusura connessione socket*/})
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurface
            )
        )

    }

    @Preview
    @Composable
    fun TopBarGamePadPreview(){
        GamePadTCPUDPTheme {
            TopBarGamePad("Connessione PC")
        }
    }
