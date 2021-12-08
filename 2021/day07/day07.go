package day07

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"sort"
)

type CalcFuelCost func(input []int, place int) int

// Part01
func Part01(input []int) int {
	return findMostEfficientPlace(input, calcFuelCost)
}

// Part02
func Part02(input []int) int {
	return findMostEfficientPlace(input, calcFuelCostSummation)
}

func findMostEfficientPlace(input []int, calcFuelCost CalcFuelCost) int {
	sort.Ints(input)
	middle := len(input) / 2

	// Try if left of middle has a smaller fuel cost
	currentCost := calcFuelCost(input, middle)
	location := middle
	for {
		location--
		leftCost := calcFuelCost(input, location)
		if leftCost >= currentCost {
			break
		}
		currentCost = leftCost
	}

	// Try if right of middle has a smaller fuel cost
	location = middle
	for {
		location++
		rightCost := calcFuelCost(input, location)
		if rightCost >= currentCost {
			break
		}
		currentCost = rightCost
	}

	return currentCost
}

// calcFuelCost calculates total fuel cost for given place
// Where 1 place cost 1 fuel
func calcFuelCost(crabPlaces []int, place int) int {
	totalFuelCost := 0

	for _, crabPlace := range crabPlaces {
		totalFuelCost += shared.IntAbs(place - crabPlace)
	}

	return totalFuelCost
}

// calcFuelCostSummation calculates total fuel cost for given place
// Where each extra place step cost 1 extra fuel
func calcFuelCostSummation(crabPlaces []int, place int) int {
	totalFuelCost := 0

	for _, crabPlace := range crabPlaces {
		cost := shared.IntAbs(place - crabPlace)
		totalFuelCost += cost * (cost + 1) / 2 // Summation
	}

	return totalFuelCost
}