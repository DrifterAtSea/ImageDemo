package io.johannrosenberg.imagedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.johannrosenberg.imagedemo.ui.main.ImageScreen
import io.johannrosenberg.imagedemo.ui.theme.ImageDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //enableEdgeToEdge()
    setContent {
      ImageDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          ImageScreen()
        }
      }
    }
  }
}
