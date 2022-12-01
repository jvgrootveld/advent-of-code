package day01

class Day01 {

    companion object {
    
        fun part1(input: List<String>): Int {

            var currentCalories = 0
            var highestCalories = 0

            for (line in input) {
                if (line.isEmpty()) {
                    // Set and reset
                    highestCalories = highestCalories.coerceAtLeast(currentCalories);
                    currentCalories = 0
                } else {
                    currentCalories += line.toInt()
                }
            }

            return highestCalories.coerceAtLeast(currentCalories);
        }

        fun part2(input: List<String>): Int {
            var currentCalories = 0
            var calorieGroups = mutableListOf<Int>()

            for (line in input) {
                if (line.isEmpty()) {
                    // Set and reset
                    calorieGroups.add(currentCalories)
                    currentCalories = 0
                } else {
                    currentCalories += line.toInt()
                }
            }

            // Add last
            calorieGroups.add(currentCalories)

            return calorieGroups
                .sorted()
                .reversed()
                .take(3)
                .sum()
        }
    }
}