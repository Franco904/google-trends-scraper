package com.example.scrapingapi.service

import com.example.scrapingapi.model.Port
import com.example.scrapingapi.model.PortsScrapingResult
import com.example.scrapingapi.util.formatPort
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.Result
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import it.skrape.selects.DocElement
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
        val portTables = table(".wikitable") { findAll { this } }

        for (table in portTables) {
            val portRowsRaw = table.tr { findAll { this } }

            // Retira a linha de cabeçalho da tabela
            val portRows = portRowsRaw.drop(1)

            for (row in portRows) {
                val portRowValues = row.td { findAll { this } }

                val number = portRowValues[0].text.formatPort()
                val description = portRowValues[1].text
                val status = getPortStatus(portRowValues)

                result.ports.add(
                    Port(number, description, status)
                )
            }
        }
    }

    private fun getPortStatus(portRowValues: List<DocElement>) = try {
        val status = portRowValues[2].text
        status.ifEmpty { "Não informado" }
    } catch (e: IndexOutOfBoundsException) {
        "Não informado"
    }

    companion object {
        // URL da página estática
        const val WIKIPEDIA_URL = "https://pt.wikipedia.org/wiki/Lista_de_portas_dos_protocolos_TCP_e_UDP"
    }
}
