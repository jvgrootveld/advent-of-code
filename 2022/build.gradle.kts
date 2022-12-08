import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "nl.justinform"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("MainKt")
}

// Add new day template task

val day: String by project

task("addDay") {
    doFirst {
        if (!project.hasProperty("day")) {
            error("Missing required argument 'day'")
        }

        val paddedDay = day.padStart(2, '0')
        println("Create Day$paddedDay")

        createFile(
            "src/main/kotlin/day$paddedDay/Day$paddedDay.kt",
            """
        package day$paddedDay

        class Day$paddedDay {

            companion object {
            
                fun part1(input: List<String>): Int {
                    TODO("IMPLEMENT")
                }
        
                fun part2(input: List<String>): Int {
                    TODO("IMPLEMENT")
                }
            }
        }
    """.trimIndent()
        )

        createFile(
            "src/test/resources/day$paddedDay.txt",
            "Replace with day$paddedDay puzzle input"
        )

        createFile(
            "src/test/resources/day${paddedDay}_test.txt",
            "Replace with day$paddedDay puzzle test input"
        )

        createFile(
            "src/test/kotlin/Day${paddedDay}Test.kt",
            """
import day$paddedDay.Day$paddedDay
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day${paddedDay}Test {

    private val input = Resource.readFileLines("day$paddedDay")
    private val testInput = Resource.readFileLines("day${paddedDay}_test")
    
    
    @ParameterizedTest
    @CsvSource(
        "input, 0",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day$paddedDay.part1(listOf(input)))
    }
    
    @Test
    fun `part1 examples`() {
        assertEquals(0, Day$paddedDay.part1(testInput))
    }

    @Test
    fun `part1 puzzel input`() {
        assertEquals(0, Day$paddedDay.part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "input, 0",
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, Day$paddedDay.part2(listOf(input)))
    }
    
    @Test
    fun `part2 examples`() {
        assertEquals(0, Day$paddedDay.part2(testInput))
    }

    @Test
    fun `part2 puzzel input`() {
        assertEquals(0, Day$paddedDay.part2(input))
    }
}
    """.trimIndent()
        )
    }
}

fun createFile(path: String, content: String) {
    val file = File(path)
    file.parentFile.mkdirs()

    // Create and check new file
    val isNewFileCreated :Boolean = file.createNewFile()

    if(!isNewFileCreated){
        error("$file already exists.")
    }

    file.writeText(content)
}