package com.dimhans.dimhansiddapp

import ProgressViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dimhans.dimhansiddapp.helpscreens.YouTubeVideoPlayer

@Composable
fun ToothpasteScreen(progressViewModel: ProgressViewModel) {
    var isChecked by remember {
        mutableStateOf(progressViewModel.completedTasks.value >= 1)
    }

    Scaffold(
        topBar = { MyTopAppBar() }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
            Text("Apply Toothpaste", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            YouTubeVideoPlayer(
                videoId = "fHSo2cpyIas",
                modifier = Modifier.fillMaxWidth()
            ) // your video
            Spacer(Modifier.height(16.dp))
            Text("Step 1: Open toothpaste cap...\nStep 2: Squeeze a pea-sized amount...")
            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { checked ->
                        isChecked = checked
                        progressViewModel.updateTaskCompletion(checked)
                    }
                )
                Text("Mark as Completed")
            }
        }
    }

}

@Composable
fun FrontTeethScreen() {
    Scaffold(
        topBar = { MyTopAppBar() }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
            Text("Brush Front Teeth", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            YouTubeVideoPlayer(videoId = "3JZ_D3ELwOQ", modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            Text("Step 1: Place brush at 45°...\nStep 2: Move gently up and down...")
        }
    }
}

@Composable
fun BackTeethScreen() {
    Scaffold(
        topBar = { MyTopAppBar() }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Text("Brush Back Teeth", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            YouTubeVideoPlayer(videoId = "lXMskKTw3Bc", modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            Text("Step 1: Angle brush correctly...\nStep 2: Focus on molars...")
        }
    }
}
