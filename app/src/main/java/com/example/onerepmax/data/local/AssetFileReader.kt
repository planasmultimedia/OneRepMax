package com.example.onerepmax.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AssetFileReader @Inject constructor(
    @ApplicationContext private val context: Context
) : FileReader {
    override fun readLines(fileName: String): List<String> {
        return context.assets.open(fileName).bufferedReader().useLines { it.toList() }
    }
}