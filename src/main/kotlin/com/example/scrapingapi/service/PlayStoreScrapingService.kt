package com.example.scrapingapi.service

import com.example.scrapingapi.model.result.ReviewsScrapingResult
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.Result
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.div
import org.springframework.stereotype.Service

@Service
class PlayStoreScrapingService {
    fun getReviewComment(): String? {
        val scrapingResult = skrape(HttpFetcher) {
            request { url = PLAY_STORE_URL }

            extractIt<ReviewsScrapingResult> { result -> fromHtml(result) }
        }

        return scrapingResult.reviewComment
    }

    private fun Result.fromHtml(result: ReviewsScrapingResult) = htmlDocument {
        val reviewComment = div(".h3YV2d") { findFirst { this } }.text

        result.reviewComment = reviewComment
    }

    companion object {
        const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=br.gov.saude.acs&hl=en_US"
    }
}
