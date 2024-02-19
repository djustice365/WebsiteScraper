plugins {
    kotlin("jvm") version "1.9.22"
}

group = "ej"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.15.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
