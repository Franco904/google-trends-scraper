package com.example.scrapingapi.util

fun String.formatPort(): String {
    return Regex("[^(0-9-)]*").replace(this, "")
}


