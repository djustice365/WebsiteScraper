package ej.webscraper.services

class CSSQueryService {

    fun elementWithAttribute(element: String, attribute: String): String {
        return "$element[$attribute]"
    }

    fun attributeEquals(element: String, attribute: String, value: String): String {
        return elementWithAttribute(element, "$attribute=$value")
    }

    fun attributeLike(element: String, attribute: String, value: String): String {
        return elementWithAttribute(element, "$attribute*=$value")
    }

    fun attributeStartsWith(element: String, attribute: String, value: String): String {
        return elementWithAttribute(element, "$attribute^=$value")
    }

    fun attributeRegex(element: String, attribute: String, regex: String): String {
        return elementWithAttribute(element, "$attribute~=$regex")
    }

}