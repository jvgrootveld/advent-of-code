package day04

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import org.gradle.internal.impldep.org.apache.commons.codec.digest.DigestUtils
import java.util.concurrent.atomic.AtomicInteger

class Day04 {
    companion object {

        private const val FIND_PART1_PREFIX = "00000"
        private const val FIND_PART2_PREFIX = "000000"

        fun part1(input: String): Int {
            return findMd5HexWithPrefix(input, FIND_PART1_PREFIX)
        }

        fun part2(input: String): Int {
            return findMd5HexWithPrefix(input, FIND_PART2_PREFIX)
        }

        /**
         * Part 1: 395 ms
         * Part 2: 664 ms
         */
        private fun findMd5HexWithPrefix(input: String, prefix: String): Int {
            var number = 0
            do {
                number++
                var hexResult = DigestUtils.md5Hex(input + number)
            } while (!hexResult.startsWith(prefix))

            return number
        }

        fun CoroutineScope.produceNumbers(input: String, prefix: String) = produce {
            var i = 0
            while (true) {
                val hexResult = DigestUtils.md5Hex(input + i)
                if (hexResult.startsWith(prefix)) {
                    send(i)
                } else {
                    send(0)
                }
                i++
            }
        }

        var matchedResult = AtomicInteger(-1)

        private fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
            for (result in channel) {
                if (result > 0) {
                    matchedResult.set(result)
                    channel.cancel()
                }
            }
        }

        /**
         * Part 1: 2088 ms
         * Part 2: 2090 ms
         */
        private fun findWithCoroutines(input: String, prefix: String): Int = runBlocking {
            val producer = produceNumbers(input, prefix)

            repeat(10) {
                launchProcessor(it, producer)
            }

            delay(2000)
            producer.cancel() // Cancel after timeout

            return@runBlocking matchedResult.get()
        }
    }
}