package com.example.datastorebugreport

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.datastorebugreport.data.FirstDataStoreSingleton
import com.example.datastorebugreport.data.SecondDataStoreSingleton
import com.example.datastorebugreport.service.SampleService
import com.example.datastorebugreport.ui.theme.DataStoreBugReportTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreBugReportTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        startService(Intent(this, SampleService::class.java))
        lifecycleScope.launch {
            while (true) {
                delay(2000)
                FirstDataStoreSingleton.getInstance(this@MainActivity).dataStore.updateData {
                    it.copy(version = it.version + 1)
                }
                SecondDataStoreSingleton.getInstance(this@MainActivity).dataStore.updateData {
                    it.copy(version = it.version + 1)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DataStoreBugReportTheme {
        Greeting("Android")
    }
}