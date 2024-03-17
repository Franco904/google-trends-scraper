package com.example.scrapingapi.controller

import com.example.scrapingapi.model.Port
import com.example.scrapingapi.service.WikipediaScrapingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ports")
class PortsController(
    private val wikipediaScrapingService: WikipediaScrapingService,
) {
    @GetMapping
    fun getPorts(): List<Port> = wikipediaScrapingService.getPortsData()
}
