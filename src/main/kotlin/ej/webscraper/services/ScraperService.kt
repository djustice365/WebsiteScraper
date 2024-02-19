package ej.webscraper.services

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ScraperService constructor(url: String) {
    private var doc: Document = Jsoup.connect(url).get()

    fun getElements(selector: String): Elements {
        return doc.select(selector)
    }

}