package ej.webscraper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebscraperApplication

fun main(args: Array<String>) {
	runApplication<WebscraperApplication>(*args)
}
