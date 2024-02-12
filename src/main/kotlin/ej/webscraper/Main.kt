package ej.webscraper

import ej.webscraper.models.CardData
import ej.webscraper.services.PokemonService

fun main(args: Array<String>) {
    val productionNumber = "58/102"
    val firstEdition: Boolean = false
    val pokemonService = PokemonService()
    val nameAndBase = pokemonService.scrapeNameAndBase(productionNumber)
    println("Name and base: $nameAndBase")
    val variants = pokemonService.scrapeVariants(nameAndBase.base, nameAndBase.name)
    println("Variants: $variants")

    val variant = "Shadowless Red Cheeks"
    // choose variant
    println("Chosen variant: $variant")

    val data: CardData = pokemonService.scrape(productionNumber, variant)
    println("Data: $data")
//    val data:CardData = pokemonService.scrape(productionNumber, firstEdition)
//    val variants = pokemonService.scrapeVariants(data.base, data.name)
//    println(variants)
}
