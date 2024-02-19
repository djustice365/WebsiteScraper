//package ej.webscraper.services
//
//import org.jsoup.select.Elements
//import ej.webscraper.enums.Condition
//import ej.webscraper.models.CardData
//import ej.webscraper.models.NameBaseDTO
//import java.net.URLEncoder
//import java.nio.charset.StandardCharsets
//import kotlin.math.ceil
//
//class ExampleUsage {
//    // Google
//    private val googleURL = "https://www.google.com/search?client=firefox-b-1-d&q=pokemon+price+%s+%s"
//    private val site = "site%3Apricecharting.com"
//    private val linkSelector = "a[href]"
//
//    // Price Chart
//    private val priceChartSite = "www.pricecharting.com"
//    private val consoleUrl = "https://www.pricecharting.com/console/%s?cursor=%s"
//    private val totalSelector = "div#console-header p:nth-of-type(5)"
//    private val consoleListSelector = "table#games_table tbody tr td.title a"
//
//    private val headerSelector = "table#price_data thead:first-of-type tr th"
//    private val nameSelector = "h1#product_name"
//    private val baseSelector = "h1#product_name a"
//    private val priceSelector = "table#price_data tbody:first-of-type tr:first-of-type span.price"
//    private val ungraded = "Ungraded"
//    private val psa10 = "PSA 10"
//
//    // Bulbapedia
//    private val bulbURL = "https://bulbapedia.bulbagarden.net/wiki/%s_(Pokémon)"
//    private val numberSelector = "a[title=List of Pokémon by National Pokédex number] span"
//
//    fun scrapeNameAndBase(productionNumber: String): NameBaseDTO {
//        val url = formatGoogle(productionNumber, "")
//        var scraper = ScraperService(url)
//        val links: Elements = scraper.getElements(linkSelector)
//        val priceChartLink = links.stream()
//            .filter { it.text().contains(priceChartSite)}
//            .toList()[0].attr("abs:href")
//
//        scraper = ScraperService(priceChartLink)
//
//        return NameBaseDTO(parseName(scraper), parseBase(scraper))
//    }
//
//    fun scrape(productionNumber: String, variant: String): CardData {
//        val url = formatGoogle(productionNumber, variant)
//        val scraper = ScraperService(url)
//        val selector = "a[href]"
//        val links: Elements = scraper.getElements(selector)
//        val filtered = links.stream()
//            .filter { it.text().contains(priceChartSite) && it.text().contains(variant) }
//            .toList()[0]
//
//        val data = scrapeData(filtered.attr("abs:href"))
//        data.productionNumber = productionNumber
//        data.variant = variant
//
//        return data
//    }
//
//    fun scrapeVariants(base: String, name: String): List<String> {
//        var loops = 10
//        var cursor = 0
//        var cardsFound: List<String> = ArrayList()
//        var x = 0
//
//        while (x < loops) {
//            val url: String = String.format(consoleUrl, base.replace(" ", "-"), cursor)
//            val scraper = ScraperService(url)
//
//            val total = scraper.getElements(totalSelector).stream().map {
//                val text = it.text().toString()
//                text.substring(text.lastIndexOf("/") + 1, text.indexOf("i")).trim()
//            }.toList()[0].toDouble()
//            loops = ceil(total / 50).toInt()
//
//            val list = scraper.getElements(consoleListSelector).map { it.text() }.toList()
//            cursor += list.size
//            cardsFound = cardsFound.plus(list)
//            x++
//        }
//
//        return listOf("---").plus(cardsFound.stream()
//            .filter { it.contains(name) && it.contains("[") }
//            .map { it.substring(it.lastIndexOf("[") + 1, it.lastIndexOf("]")) }
//            .toList())
//    }
//
//    private fun scrapeData(url: String): CardData {
//        val scraper = ScraperService(url)
//
//        val name: String = parseName(scraper)
//        val base: String = parseBase(scraper)
//        val number: Int = parseNumber(name)
//        val prices: HashMap<String, String> = parsePrices(scraper)
//
//        return CardData(
//            name,
//            number,
//            "",
//            base,
//            Condition.NOT_RATED,
//            "",
//            prices[ungraded]!!,
//            prices[psa10]!!,
//            0
//        )
//    }
//
//    private fun parseName(scraper: ScraperService): String {
//        val foundName = scraper.getElements(nameSelector)
//            .stream()
//            .toList()[0]
//            .text().split("#")[0]
//
//        return  if (foundName.contains("["))
//                    foundName.substring(0, foundName.lastIndexOf("[")).trim()
//                else
//                    foundName.trim()
//    }
//
//    private fun parseBase(scraper: ScraperService): String {
//        return scraper.getElements(baseSelector)
//            .stream()
//            .toList()[0]
//            .text()
//    }
//
//    private fun parseNumber(name: String): Int {
//        val scraper = ScraperService(String.format(bulbURL, name))
//        val number: Elements = scraper.getElements(numberSelector)
//        return number.stream().filter{ it.text().contains("#")}.map { it.text() }
//            .toList()[0]
//                .substring(1)
//                .toInt()
//    }
//
//    private fun parsePrices(scraper: ScraperService): HashMap<String, String> {
//        val prices: HashMap<String, String> = HashMap()
//        val sections: HashMap<Int, String> = HashMap()
//        val headers: Elements = scraper.getElements(headerSelector)
//        for ((index, result) in headers.withIndex()) {
//            if (result.text().contains(ungraded) || result.text().contains(psa10)) {
//                sections[index] = result.text()
//            }
//        }
//
//        val results: Elements = scraper.getElements(priceSelector)
//
//        for (section in sections) {
//            prices[section.value] = results[section.key].text()
//        }
//
//        return prices
//    }
//
//    private fun formatGoogle(decodedProductionNumber: String, variant: String): String {
//        val encoded = urlEncode(decodedProductionNumber)
//        val converted = variant.replace(" ", "+")
//        return String.format(googleURL, "$encoded$converted", site)
//
//    }
//
//    private fun urlEncode(text: String): String {
//        return URLEncoder.encode(text, StandardCharsets.UTF_8.toString())
//    }
//}