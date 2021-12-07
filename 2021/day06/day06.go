package day06

import (
	"fmt"
)

// Part01
func Part01(input []int, days int) int {
	var school []int

	for _, in := range input {
		school = append(school, in)
	}

	//fmt.Printf("Initial state: %v\n", school)
	for currentDay := 1; currentDay <= days; currentDay++ {
		currentLength := len(school)
		for i := 0; i < currentLength; i++ {
			school[i]--

			if school[i] < 0 {
				// Reset
				school[i] = 6
				// Add new fish
				school = append(school, 8)
			}
		}
		//fmt.Printf("After %2d days: %v\n", currentDay, school)
	}

	return len(school)
}

// Part02
func Part02(input []int, days int) int {
	fmt.Println("HO HO HO!")

	// TODO need better algorithm for 265 days

	return 0
}
