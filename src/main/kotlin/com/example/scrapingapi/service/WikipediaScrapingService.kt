package com.example.scrapingapi.service

import com.example.scrapingapi.model.Port
import com.example.scrapingapi.model.PortsScrapingResult
import com.example.scrapingapi.util.removeNonDigitsChars
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.Result
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.table
import it.skrape.selects.html5.td
import it.skrape.selects.html5.tr
import org.springframework.stereotype.Service

/**
 * Referências:
 * https://www.scrapingbee.com/blog/web-scraping-kotlin/
 * https://docs.skrape.it/docs/http-client/overview
 * */

@Service
class WikipediaScrapingService {
    fun getPortsData(): List<Port> {
        val scrapingResult = skrape(HttpFetcher) {
            request { url = WIKIPEDIA_URL }

            extractIt<PortsScrapingResult> { result -> fromHtml(result) }
        }

        return scrapingResult.ports
    }

    private fun Result.fromHtml(result: PortsScrapingResult) = htmlDocument {
        val firstPortsTable = table(".wikitable") { findFirst { this } }
        val firstPortsRows = firstPortsTable.tr { findAll { this } }

        // Retira a linha de cabeçalho da tabela
        val firstPorts = firstPortsRows.drop(1)

        firstPorts.map { portLine ->
            val values = portLine.td { findAll { this } }

            val number = values[0].text.removeNonDigitsChars()
            val description = values[1].text
            val status = values[2].text

            result.ports.add(
                Port(number, description, status)
            )
        }
    }

    companion object {
        // URL da página estática
        const val WIKIPEDIA_URL = "https://pt.wikipedia.org/wiki/Lista_de_portas_dos_protocolos_TCP_e_UDP"
    }
}
