package uz.tezpos.cache

import android.content.Context
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object Cache {
    fun readStringFromCache(context: Context, filename: String): String? {
        val file = File(context.cacheDir, filename)
        if (!file.exists()) return null
        val reader = BufferedReader(FileReader(file.absoluteFile))
        return reader.use { it.readText() }
    }

    fun writeStringToCache(context: Context, filename: String, content: String) {
        val file = File(context.cacheDir, filename)
        val writer = BufferedWriter(FileWriter(file.absoluteFile))
        writer.use { it.write(content) }
    }
}