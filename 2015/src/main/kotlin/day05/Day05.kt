package day05

class Day05 {
    companion object {

        fun part1(input: List<String>): Int = calcAmountOfNiceStrings(input, ::isStringNicePart1)
        fun part2(input: List<String>): Int = calcAmountOfNiceStrings(input, ::isStringNicePart2)

        private fun calcAmountOfNiceStrings(input: List<String>, niceStringChecker: (String) -> Boolean): Int {
            var amountOfNiceStrings = 0

            input.forEach {
                if (niceStringChecker(it)) {
                    amountOfNiceStrings++
                }
            }

            return amountOfNiceStrings
        }

        private const val vowels = "aeiou"
        private val naughtyCombinations = listOf("ab", "cd", "pq", "xy")

        /**
         * Check if value is a nice String. Conditions:
         * It contains at least three vowels (aeiou only),
         *   like aei, xazegov, or aeiouaeiouaeiou.
         * It contains at least one letter that appears twice in a row,
         *   like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
         * It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
         */
        fun isStringNicePart1(value: String): Boolean {
            var vowelsCount = 0
            var lettersInARowCount = 0
            var previousChar = '-'

            for (char in value) {
                if (char in vowels) vowelsCount++
                if (previousChar == char) lettersInARowCount++
                if ("$previousChar$char" in naughtyCombinations) return false

                previousChar = char
            }

            return vowelsCount > 2 && lettersInARowCount > 0
        }

        /**
         * Check if value is a nice String. Conditions:
         * It contains a pair of any two letters that appears at least twice in the string without overlapping,
         *   like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
         * It contains at least one letter which repeats with exactly one letter between them,
         *   like xyx, abcdefeghi (efe), or even aaa.
         */
        fun isStringNicePart2(value: String): Boolean {
            var containsPair = false
            var containsLetterBetween = false


            // Start at 1 because we check the previous
            // Stop at 1 before end because we check the next
            for (i in 1 until value.length - 1) {

                // Check if this+previous chars occurs in the future
                if(!containsPair && value.substring(i+1).contains("${value[i-1]}${value[i]}"))    {
                    containsPair = true
                }

                // Check if previous and next are the same
                if (value[i-1] == value[i+1]) {
                    containsLetterBetween = true
                }

                // If found break and return
                if (containsPair && containsLetterBetween) {
                    return true
                }
            }

            return false
        }
    }
}