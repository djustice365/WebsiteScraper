package org.justice.website.scraper.models

import org.justice.website.scraper.enums.Condition

data class CardData(
    var name: String,
    var number: Int,
    var productionNumber: String,
    var base: String,
    var condition: Condition,
    var variant: String,
    var lowPrice: String,
    var highPrice: String,
    var quantity: Int,
)
