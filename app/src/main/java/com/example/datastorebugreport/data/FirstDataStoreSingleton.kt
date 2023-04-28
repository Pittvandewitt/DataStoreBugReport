package com.example.datastorebugreport.data

import android.content.Context

class FirstDataStoreSingleton private constructor(context: Context) {

    val dataStore = context.getDataStoreForName(context.packageName + "_preferences")

    companion object {

        @Volatile
        private var INSTANCE: FirstDataStoreSingleton? = null

        fun getInstance(context: Context): FirstDataStoreSingleton {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = FirstDataStoreSingleton(context)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}