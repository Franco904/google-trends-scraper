package com.example.scrapingapi.util

fun String.removeNonDigitsChars(): Int {
    return Regex("\\D*").replace(this, "").toInt()
}
