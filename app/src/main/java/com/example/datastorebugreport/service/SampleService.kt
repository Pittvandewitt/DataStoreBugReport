package com.example.datastorebugreport.service

import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.datastorebugreport.data.FirstDataStoreSingleton
import com.example.datastorebugreport.data.SecondDataStoreSingleton
import kotlinx.coroutines.launch

class SampleService : LifecycleService() {

    override fun onCreate() {
        super.onCreate()
        lifecycleScope.launch {
            FirstDataStoreSingleton.getInstance(this@SampleService).dataStore.data.collect {
                Log.i("BUGREPORT", "FirstDataStoreSingleton data: $it")
            }
        }
        lifecycleScope.launch {
            SecondDataStoreSingleton.getInstance(this@SampleService).dataStore.data.collect {
                Log.i("BUGREPORT", "SecondDataStoreSingleton data: $it")
            }
        }
    }
}