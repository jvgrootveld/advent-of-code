package day06

import "github.com/jvgrootveld/advent-of-code/avent2021/shared"

func Part01(input []int, days int) int {
	totalFish := 0

	cache := make(map[int]int)

	for _, daysLeft := range input {
		totalFish += countFish(days - daysLeft, cache)
	}

	return totalFish
}

func Part02(input []int, days int) int {
	school := make([]int, 9)

	for _, daysLeft := range input {
		school[daysLeft]++
	}

	for currentDay := 1; currentDay <= days; currentDay++ {

		// Fish to birth
		toBirth := school[0]

		// Decrease days
		for i := 1; i <= 8; i++ {
			school[i-1] = school[i]
		}

		school[6] += toBirth
		school[8] = toBirth
	}

	return shared.SumInts(school)
}


func countFish(daysLeft int, cache map[int]int) int {
	if result, ok := cache[daysLeft]; ok {
		return result
	}

	// Count this fish
	total := 1

	for i := 0; i < daysLeft; i += 7 {
		// -9 for first new fish
		total += countFish(daysLeft - 9 - i, cache)
	}

	cache[daysLeft] = total

	return total
}