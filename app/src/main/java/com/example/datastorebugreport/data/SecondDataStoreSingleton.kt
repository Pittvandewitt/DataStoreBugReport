package com.example.datastorebugreport.data

import android.content.Context

class SecondDataStoreSingleton private constructor(context: Context){

    val dataStore = context.getDataStoreForName(context.packageName + "_preferences2")

    companion object {

        @Volatile
        private var INSTANCE: SecondDataStoreSingleton? = null

        fun getInstance(context: Context): SecondDataStoreSingleton {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = SecondDataStoreSingleton(context)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}