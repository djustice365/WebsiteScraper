package org.justice.website.scraper.services

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ScraperService(url: String) {
    private val doc: Document

    init {
//        println("Fetching url: $url")
        doc = Jsoup.connect(url).get()
    }

    fun getElements(selector: String): Elements {
        return doc.select(selector)
    }

}