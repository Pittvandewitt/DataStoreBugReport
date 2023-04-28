package com.example.datastorebugreport.data

import android.content.Context
import androidx.datastore.core.ExperimentalMultiProcessDataStore
import androidx.datastore.core.MultiProcessDataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.InputStream
import java.io.OutputStream

data class Settings(val version: Int)

object Serializer: Serializer<Settings> {
    override val defaultValue: Settings
        get() = Settings(0)

    override suspend fun readFrom(input: InputStream): Settings {
        val version = input.use {
            it.read()
        }
        return Settings(version)
    }

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        output.use {
            it.write(t.version)
        }
    }
}

@OptIn(ExperimentalMultiProcessDataStore::class)
fun Context.getDataStoreForName(
    name: String,
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
) = MultiProcessDataStoreFactory.create(
    serializer = Serializer,
    scope = scope,
) {
    dataStoreFile(name)
}