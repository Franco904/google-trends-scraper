package com.example.scrapingapi.service

import org.springframework.stereotype.Service

@Service
class GoogleTrendsScrapingService {
    fun getTrend(): String = ""

    companion object {
        const val GOOGLE_TRENDS_URL = "https://trends.google.com.br/trends/explore?geo=BR&q=gaza&hl=pt"
    }
}
