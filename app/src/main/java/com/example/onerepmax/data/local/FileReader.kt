package com.example.onerepmax.data.local

interface FileReader {
    fun readLines(fileName: String): List<String>
}