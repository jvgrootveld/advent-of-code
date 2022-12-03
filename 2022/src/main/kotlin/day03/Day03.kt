package day03

import java.lang.RuntimeException

class Day03 {

    companion object {

        private val itemPriorities = (('a'..'z') + ('A'..'Z')).withIndex().associate { (i, c) -> c to i + 1 }

        fun part1(input: List<String>): Int {
            return input
                .map { it.chunked(it.length / 2) }
                .map(this::findCommonItem)
                .sumOf { itemPriorities.getOrDefault(it, 0) }
        }

        fun part2(input: List<String>): Int {
            return input
                .chunked(3)
                .map(this::findCommonItem)
                .sumOf { itemPriorities.getOrDefault(it, 0) }
        }

        private fun findCommonItem(groupOfItems: List<String>): Char {
            val firstGroup = groupOfItems.first()
            val otherGroups = groupOfItems.subList(1, groupOfItems.size)

            for (item in firstGroup) {
                val amountOfMatchesInOtherGroups = otherGroups
                    .filter { it.contains(item) }.size

                if (amountOfMatchesInOtherGroups == otherGroups.size) {
                    return item
                }
            }

            throw RuntimeException("No common item found. Groups: $groupOfItems")
        }

    }
}