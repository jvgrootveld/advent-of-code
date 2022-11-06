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

        val dayString = "day${day.padStart(2, '0')}"
        println("Create day: ${dayString.capitalized()}")

        createFile(
            "src/main/kotlin/$dayString/${dayString.capitalized()}.kt",
            """
        package $dayString

        class ${dayString.capitalized()} {

            companion object {
            
                fun part1(input: String): Int {
                    TODO("IMPLEMENT")
                }

                fun part2(input: String): Int {
                    TODO("IMPLEMENT")
                }
            }
        }
    """.trimIndent()
        )

        createFile(
            "src/test/resources/$dayString.txt",
            "Replace with $dayString puzzle input"
        )

        createFile(
            "src/test/kotlin/${dayString.capitalized()}Test.kt",
            """
import $dayString.${dayString.capitalized()}
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day02Test {

    @ParameterizedTest
    @CsvSource(
        "input, 0",
    )
    fun `part1 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, ${dayString.capitalized()}.part1(input))
    }

    @Test
    fun `part1 puzzel input`() {
        val input = Resource.readFile("$dayString")
        assertEquals(0, ${dayString.capitalized()}.part1(input))
    }

    @ParameterizedTest
    @CsvSource(
        "input, 0",
    )
    fun `part2 examples`(input: String, expectedResult: Int) {
        assertEquals(expectedResult, ${dayString.capitalized()}.part2(input))
    }

    @Test
    fun `part2 puzzel input`() {
        val input = Resource.readFile("$dayString")
        assertEquals(0, ${dayString.capitalized()}.part2(input))
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