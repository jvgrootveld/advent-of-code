package day04

class Day04 {

    companion object {

        fun part1(input: List<String>): Int {
            return input
                .map(this::parsePair).count { pair ->
                    countIntersect(pair).let {
                        it == pair.first.count() || it == pair.second.count()
                    }
                }
        }

        fun part2(input: List<String>): Int {
            return input
                .map(this::parsePair).count { pair ->
                    countIntersect(pair) > 0
                }
        }

        private fun countIntersect(pair: Pair<IntRange, IntRange>): Int {
            return pair.first.count {
                it in pair.second
            }
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