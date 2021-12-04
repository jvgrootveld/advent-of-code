package day01

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
)

// Part01
// How many measurements are larger than the previous measurement?
func Part01(input []int) int {
	areLarger := 0

	for i := 1; i < len(input); i++ {
		if input[i] > input[i-1] {
			areLarger++
		}
	}

	return areLarger
}

// Part02
// Consider sums of a three-measurement sliding window. How many sums are larger than the previous sum?
func Part02(input []int) int {
	var grouped []int

	for i := 0; i < len(input) - 2; i++ {
		summed := shared.SumInts(input[i : i+3])
		grouped = append(grouped, summed)
	}

	return Part01(grouped)
}
