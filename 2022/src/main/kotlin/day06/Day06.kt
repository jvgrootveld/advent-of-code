package day06

import java.util.*

class Day06 {

    companion object {

        fun part1(input: String): Int {
            return findStartOfPacketMarker(input, 4)
        }

        fun part2(input: String): Int {
            return findStartOfPacketMarker(input, 14)
        }

        private fun findStartOfPacketMarkerIdiomatic(message: String, markerSize: Int): Int {
            return markerSize + message
                .windowed(markerSize)
                .indexOfFirst {
                    it.toSet().size == markerSize
                }
        }

        private fun findStartOfPacketMarker(message: String, markerSize: Int): Int {
            val messageQueue = ArrayDeque<Char>(markerSize)

            var position = 0
            for (c in message) {

                if (messageQueue.size == markerSize) {
                    return position
                }

                while (messageQueue.contains(c)) {
                    messageQueue.pop()
                }

                messageQueue.add(c)
                position++
            }

            return -1
        }
    }
}