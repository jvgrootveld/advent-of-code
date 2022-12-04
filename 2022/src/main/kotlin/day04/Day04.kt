package day04

class Day04 {

    companion object {

        fun part1(input: List<String>): Int {
            return input
                .map(this::parsePair).count {
                    it.first intersectAll it.second
                }
        }

        fun part2(input: List<String>): Int {
            return input
                .map(this::parsePair).count {
                    it.first intersectAny it.second
                }
        }

        /**
         * Return true if a range fully intersects the other.
         */
        private infix fun IntRange.intersectAll(range: IntRange) = this.count {
            it in range
        }.let {
            it == this.count() || it == range.count()
        }

        /**
         * Return true if the two ranges intersect at any place.
         */
        private infix fun IntRange.intersectAny(range: IntRange): Boolean {
            for (value in this) {
                if (value in range) {
                    return true
                }
            }

            return false
        }

        // Parse range pair like: 2-4,6-8
        private fun parsePair(value: String): Pair<IntRange, IntRange> {
            val (left, right) = value.split(',')

            return Pair(
                parseRange(left),
                parseRange(right)
            )
        }

        // Parse range like: 2-4
        private fun parseRange(range: String): IntRange {
            return range.split('-').let {
                it[0].toInt()..it[1].toInt()
            }
        }
    }
}