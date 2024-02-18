package com.rzsahu.logextractor

import android.util.Log
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader


object LogExtractor {

    private const val TAG = "MyLog.LogExtractor"
    private lateinit var writer: BufferedWriter
    private lateinit var bufferedReader: BufferedReader
    private lateinit var process: Process

    fun extractAndSaveLogsContainingWord(word: String?, outputFile: File?) {
        Log.i(TAG, "extractAndSaveLogsContainingWord: $word -> $outputFile")
        try {
            process = Runtime.getRuntime().exec("logcat")
            writer = BufferedWriter(FileWriter(outputFile))
            bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String = bufferedReader.readLine()
            while (line != null) {
                if (line.contains(word!!)) {
                    writer.write(line)
                    writer.newLine() // Add newline character to separate log entries
                }
                line = bufferedReader.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop() {
        Log.i(TAG, "stop: ")
        // Close the writer
        bufferedReader.close()
        writer.close()
        process.destroy()
    }
}

