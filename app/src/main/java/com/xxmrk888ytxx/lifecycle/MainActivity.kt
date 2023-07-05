package com.xxmrk888ytxx.lifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.lifecycle.ui.theme.LifecycleTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ShareViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val screenState by viewModel.state.collectAsState()

            val superViewSize = 300.dp

            when(screenState) {

                is ScreenState.Counter -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .size(superViewSize)
                                .clip(RoundedCornerShape(screenState.count))
                                .background(Color.Yellow)
                        ) {
                            Text(text = screenState.count.toString())

                            Spacer(modifier = Modifier.weight(1f))

                            Box(
                                modifier = Modifier
                                    .size(superViewSize / 3)
                                    .clip(CircleShape)
                                    .background(Color.Blue)
                                    .clickable {
                                        viewModel.toUpdateCounterScreen()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Tap")
                            }
                        }
                    }
                }

                is ScreenState.UpdateCounter -> {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { viewModel.updateCount(10); viewModel.toCounterScreen() }) {
                            Text(text = "Update")
                        }

                        Button(onClick = { viewModel.toCounterScreen() }) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onUserReturnInApp()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        viewModel.onUserLeaveFromApp()
    }
}
