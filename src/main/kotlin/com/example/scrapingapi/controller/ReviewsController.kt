package com.example.scrapingapi.controller

import com.example.scrapingapi.service.PlayStoreScrapingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reviews")
class ReviewsController(
    private val googleTrendsScrapingService: PlayStoreScrapingService,
) {
    @GetMapping
    fun getReviewComment(): String? = googleTrendsScrapingService.getReviewComment()
}
